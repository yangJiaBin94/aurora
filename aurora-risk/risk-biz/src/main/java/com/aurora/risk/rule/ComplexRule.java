package com.aurora.risk.rule;

import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import com.aurora.common.util.StringUtil;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.enums.PolicyTypeEnum;
import com.aurora.risk.enums.RuleExecuteStatusEnum;
import com.aurora.risk.enums.RuleHitResultEnum;
import com.aurora.risk.enums.RuleLogicEnum;
import com.aurora.risk.util.AviatorEvaluatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description 复杂规则
 * @author: Nick
 * @create: 2022-03-28 10:04
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ComplexRule extends AbstractRule {

    /**
     * 子规则链
     */
    private SimpleRule simpleRuleLink;

    /**
     * 子规则决策类型
     */
    private String subRulePolicyType;

    @Override
    void verify() {

        //命中逻辑ruleLogic校验
        assert PolicyTypeEnum.RESULT.type.equals(this.getPolicyType()) && this.getRuleLogic() != null;
        //所有子规则决策类型是否相同

    }

    @Override
    void getIndexValue(RiskContextInfo riskContextInfo) {
        //复杂规则获取结果判断依据的value为子规则的结果，所以此方法调用其子规则的execute()
        simpleRuleLink.execute(riskContextInfo);
    }

    @Override
    void getRuleResult() {
        if (hasAsyncSimpleRule(simpleRuleLink)) {
            return;
        }
        String result;
        //结果模式规则
        if (PolicyTypeEnum.RESULT.type.equals(this.getPolicyType())) {
            //子规则集为结果模式
            if (PolicyTypeEnum.RESULT.type.equals(this.subRulePolicyType)) {
                //查看子规则中是否有结果为复审的规则，有则此复杂规则结果直接为复审
                if (anyOneReview(this.simpleRuleLink)) {
                    this.setResult(RuleHitResultEnum.REVIEW.hitStatus);
                    //设置执行状态为完成
                    this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
                    //命中状态暂不设定
                    this.setIsHit(null);
                    return;
                }

                //复杂规则为结果模式时其决策模式的子规则result无效，只关心子规则的命中状态即可
                if (RuleLogicEnum.ANY_ONE.code.equals(this.getRuleLogic())) {
                    //命中逻辑：任意命中则命中
                    this.setIsHit(anyOneTrue(this.simpleRuleLink));
                } else {
                    //命中逻辑：全部命中则命中
                    this.setIsHit(allTrue(this.simpleRuleLink));
                }
            } else {
                //子规则集为评分模式 子规则必须全部执行完成
                if (checkPolicyTypeIsAllScore(simpleRuleLink)) {
                    //设置执行状态为完成
                    this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
                    throw new BaseException(ResponseEnum.RISK_RULE_1007);
                }
                //获取总分数
                double sum = getScoreSum(simpleRuleLink);
                //执行复杂规则表达式
                Map<String, Object> map = new HashMap<>(16);
                map.put("value", sum);
                this.setIsHit(AviatorEvaluatorUtil.execute(this.getRuleFormula(), map));
            }
            //如果命中则取规则命中状态，未命中则取对应未命中状态
            result = this.getIsHit() ? this.getRuleHitResult() : Objects.requireNonNull(RuleHitResultEnum.getNotHitStatus(this.getRuleHitResult()));
            this.setResult(result);
            //设置执行状态为完成
            this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
            throw new BaseException(ResponseEnum.RISK_RULE_1007);
        } else {
            //评分模式规则 子规则必须全部执行完成并且全部为评分模式
            if (checkPolicyTypeIsAllScore(simpleRuleLink)) {
                //设置执行状态为完成
                this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
                throw new BaseException(ResponseEnum.RISK_RULE_1007);
            }
            result = String.valueOf(getScoreSum(simpleRuleLink));
        }
        //设置执行状态为完成
        this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
        this.setResult(result);
    }


    /**
     * 获取所有子规则评分分数和
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return double
     * @author Nick
     * @date 2022/04/01
     */
    private double getScoreSum(AbstractRule simpleRuleLink) {
        if (simpleRuleLink == null) {
            return 0;
        } else {
            return Double.parseDouble(simpleRuleLink.getResult()) + getScoreSum(simpleRuleLink.getNext());
        }
    }

    /**
     * 校验是否所有子规则都为评分模式
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return double
     * @author Nick
     * @date 2022/04/01
     */
    private boolean checkPolicyTypeIsAllScore(AbstractRule simpleRuleLink) {
        if (simpleRuleLink == null) {
            return true;
        } else {
            return StringUtil.isNumberStr(simpleRuleLink.getResult()) && checkPolicyTypeIsAllScore(simpleRuleLink.getNext());
        }
    }

    /**
     * 判断是否有还未获取到结果的子规则（executeStatus为GETTING的规则为正在等待回调结果的异步规则，异步规则结果需回调通知）
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    private boolean hasAsyncSimpleRule(AbstractRule simpleRuleLink) {
        //当前节点为null则返回false
        if (simpleRuleLink == null) {
            return false;
        } else {
            return RuleExecuteStatusEnum.GETTING.equals(simpleRuleLink.getExecuteStatus()) || hasAsyncSimpleRule(simpleRuleLink.getNext());
        }
    }

    /**
     * 判断子规则是否全部命中
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    private boolean allTrue(AbstractRule simpleRuleLink) {
        //空节点直接返回true
        if (simpleRuleLink == null) {
            return true;
        } else {
            //当前节点为true并且下一个节点也为true则返回true否则返回false
            return Boolean.TRUE.equals(simpleRuleLink.getIsHit()) && allTrue(simpleRuleLink.getNext());
        }
    }

    /**
     * 判断子规则中是否有命中的规则
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    private boolean anyOneTrue(AbstractRule simpleRuleLink) {
        //空节点直接返回true
        if (simpleRuleLink == null) {
            return false;
        } else {
            //当前节点为true或者下一个节点为true则返回true否则返回false
            return Boolean.TRUE.equals(simpleRuleLink.getIsHit()) || anyOneTrue(simpleRuleLink.getNext());
        }
    }

    /**
     * 判断子规则中是否有复审结果的的规则
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    private boolean anyOneReview(AbstractRule simpleRuleLink) {
        //空节点直接返回true
        if (simpleRuleLink == null) {
            return false;
        } else {
            //当前节点为true或者下一个节点为true则返回true否则返回false
            return RuleHitResultEnum.REVIEW.hitStatus.equals(simpleRuleLink.getResult()) || anyOneTrue(simpleRuleLink.getNext());
        }
    }
}
