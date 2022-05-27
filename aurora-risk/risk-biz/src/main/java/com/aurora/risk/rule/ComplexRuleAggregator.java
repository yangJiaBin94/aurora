package com.aurora.risk.rule;

/**
 * @author: jiaBin
 * @create: 2022-05-27 15:08
 **/
public interface ComplexRuleAggregator extends RuleAggregator {

    /**
     * 设置规则所属策略编号
     *
     * @param ruleLink : ruleLink
     * @param strategyNo : 策略编号
     * @return void
     * @author Nick
     * @date 2022/04/06
     */
    void addStrategyNoToRuleLink(AbstractRule ruleLink, String strategyNo);

    /**
     * 获取所有子规则评分分数和
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return double
     * @author Nick
     * @date 2022/04/01
     */
    double getScoreSum(AbstractRule simpleRuleLink);

    /**
     * 校验是否所有子规则都为评分模式
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return double
     * @author Nick
     * @date 2022/04/01
     */
    boolean checkPolicyTypeIsAllScore(AbstractRule simpleRuleLink);

    /**
     * 判断是否有还未获取到结果的子规则（executeStatus为GETTING的规则为正在等待回调结果的异步规则，异步规则结果需回调通知）
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    boolean hasAsyncSimpleRule(AbstractRule simpleRuleLink);

    /**
     * 判断子规则是否全部命中
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    boolean allTrue(AbstractRule simpleRuleLink);

    /**
     * 判断子规则中是否有命中的规则
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    boolean anyOneTrue(AbstractRule simpleRuleLink);

    /**
     * 判断子规则中是否有复审结果的的规则
     *
     * @param simpleRuleLink : simpleRuleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    boolean anyOneReview(AbstractRule simpleRuleLink);
}
