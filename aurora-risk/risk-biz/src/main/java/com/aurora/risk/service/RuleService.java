package com.aurora.risk.service;

import com.aurora.risk.base.model.RiskRule;

import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-28 15:33
 **/
public interface RuleService {

    /**
     * 获取复杂规则的简单规则集
     *
     * @param complexRuleId : complexRuleId
     * @return java.util.List<com.aurora.risk.rule.SimpleRule>
     * @author Nick
     * @date 2022/03/28
     */
    List<RiskRule> getSimpleRuleListByComplexRuleId(Long complexRuleId);

    /**
     * 获取策略下规则集
     *
     * @param strategyNo : strategyNo
     * @return java.util.List<com.aurora.risk.base.model.RiskRule>
     * @author Nick
     * @date 2022/03/29
    */
    List<RiskRule> getRuleListByStrategyNo(String strategyNo);
}
