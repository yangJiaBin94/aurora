package com.aurora.risk.rule;

import com.aurora.common.util.StringUtil;
import com.aurora.risk.enums.RuleExecuteStatusEnum;
import com.aurora.risk.enums.RuleHitResultEnum;
import org.springframework.stereotype.Service;

/**
 * @author: jiaBin
 * @create: 2022-05-27 15:14
 **/

@Service
public class ComplexRuleAggregatorImpl implements ComplexRuleAggregator{
    /**
     * 设置规则所属策略编号
     *
     * @param ruleLink : ruleLink
     * @return void
     * @author Nick
     * @date 2022/04/06
     */
    @Override
    public void addStrategyNoToRuleLink(AbstractRule ruleLink, String strategyNo) {
        if (ruleLink != null) {
            ruleLink.setStrategyNo(strategyNo);
            addStrategyNoToRuleLink(ruleLink.getNext(),strategyNo);
        }
    }

    /**
     * 获取所有子规则评分分数和
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return double
     * @author Nick
     * @date 2022/04/01
     */
    @Override
    public double getScoreSum(AbstractRule simpleRuleLink) {
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
    @Override
    public boolean checkPolicyTypeIsAllScore(AbstractRule simpleRuleLink) {
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
    @Override
    public boolean hasAsyncSimpleRule(AbstractRule simpleRuleLink) {
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
    @Override
    public boolean allTrue(AbstractRule simpleRuleLink) {
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
    @Override
    public boolean anyOneTrue(AbstractRule simpleRuleLink) {
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
    @Override
    public boolean anyOneReview(AbstractRule simpleRuleLink) {
        //空节点直接返回true
        if (simpleRuleLink == null) {
            return false;
        } else {
            //当前节点为true或者下一个节点为true则返回true否则返回false
            return RuleHitResultEnum.REVIEW.hitStatus.equals(simpleRuleLink.getResult()) || anyOneTrue(simpleRuleLink.getNext());
        }
    }
}
