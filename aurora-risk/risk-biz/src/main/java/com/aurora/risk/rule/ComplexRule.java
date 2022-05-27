package com.aurora.risk.rule;

import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.enums.PolicyTypeEnum;
import com.aurora.risk.enums.RuleExecuteStatusEnum;
import com.aurora.risk.enums.RuleHitResultEnum;
import com.aurora.risk.enums.RuleLogicEnum;
import com.aurora.risk.util.AviatorEvaluatorUtil;
import com.aurora.risk.util.BeanUtil;
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

    private final ComplexRuleAggregator aggregator = BeanUtil.getBean(ComplexRuleAggregator.class);

    @Override
    void verify() {

        //命中逻辑ruleLogic校验
        assert PolicyTypeEnum.RESULT.type.equals(this.getPolicyType()) && this.getRuleLogic() != null;
        //所有子规则决策类型是否相同

    }

    @Override
    void getIndexValue(RiskContextInfo riskContextInfo) {
        //复杂规则获取结果判断依据的value为子规则的结果，所以此方法调用其子规则的execute()
        aggregator.addStrategyNoToRuleLink(simpleRuleLink, this.getStrategyNo());
        simpleRuleLink.execute(riskContextInfo);

    }

    @Override
    void getRuleResult() {
        if (aggregator.hasAsyncSimpleRule(simpleRuleLink)) {
            return;
        }
        String result;
        //结果模式规则
        if (PolicyTypeEnum.RESULT.type.equals(this.getPolicyType())) {
            //子规则集为结果模式
            if (PolicyTypeEnum.RESULT.type.equals(this.subRulePolicyType)) {
                //查看子规则中是否有结果为复审的规则，有则此复杂规则结果直接为复审
                if (aggregator.anyOneReview(this.simpleRuleLink)) {
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
                    this.setIsHit(aggregator.anyOneTrue(this.simpleRuleLink));
                } else {
                    //命中逻辑：全部命中则命中
                    this.setIsHit(aggregator.allTrue(this.simpleRuleLink));
                }
            } else {
                //子规则集为评分模式 子规则必须全部执行完成
                if (aggregator.checkPolicyTypeIsAllScore(simpleRuleLink)) {
                    //设置执行状态为完成
                    this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
                    throw new BaseException(ResponseEnum.RISK_RULE_1007);
                }
                //获取总分数
                double sum = aggregator.getScoreSum(simpleRuleLink);
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
            if (aggregator.checkPolicyTypeIsAllScore(simpleRuleLink)) {
                //设置执行状态为完成
                this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
                throw new BaseException(ResponseEnum.RISK_RULE_1007);
            }
            result = String.valueOf(aggregator.getScoreSum(simpleRuleLink));
        }
        //设置执行状态为完成
        this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
        this.setResult(result);
    }

}
