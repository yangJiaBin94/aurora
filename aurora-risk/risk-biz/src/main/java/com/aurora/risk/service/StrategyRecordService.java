package com.aurora.risk.service;

import com.aurora.risk.base.model.RiskStrategyRecord;
import com.aurora.risk.strategy.StrategyLink;

/**
 * @author: Nick
 * @create: 2022-03-30 16:15
 **/
public interface StrategyRecordService {

    /**
     * 初始化策略记录
     *
     * @param merOrderNo   : merOrderNo
     * @param riskOrderNo  : riskOrderNo
     * @param strategyLink : strategyLink
     * @return RiskStrategyRecord
     * @author Nick
     * @date 2022/03/30
     */
    RiskStrategyRecord initRiskStrategyRecord(String merOrderNo, String riskOrderNo, StrategyLink strategyLink);

    /**
     * 获取策略执行记录
     *
     * @param riskOrderNo : riskOrderNo
     * @param strategyNo : strategyNo
     * @return com.aurora.risk.base.model.RiskStrategyRecord
     * @author Nick
     * @date 2022/04/02
    */
    RiskStrategyRecord getRiskStrategyRecord(String riskOrderNo, String strategyNo);
}
