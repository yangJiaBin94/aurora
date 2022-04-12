package com.aurora.risk.rule;

import com.aurora.risk.context.RiskContextInfo;

/**
 * @author: Nick
 * @create: 2022-03-28 10:01
 **/
public interface RuleInterface {

    /**
     * 执行规则
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/03/30
    */
    void execute(RiskContextInfo riskContextInfo);

}

