package com.aurora.risk.rule;

import com.aurora.risk.base.model.RiskRule;

/**
 * 规则初始化器
 *
 * @author: Nick
 * @create: 2022-03-28 15:03
 **/
public interface RuleInitializer<T> {

    /**
     * 规则初始化
     *
     * @param rule : rule
     * @return T
     * @author Nick
     * @date 2022/03/28
     */
    T initializer(RiskRule rule);
}
