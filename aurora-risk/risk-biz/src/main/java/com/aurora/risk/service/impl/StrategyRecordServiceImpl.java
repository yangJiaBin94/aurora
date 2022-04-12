package com.aurora.risk.service.impl;

import com.aurora.risk.base.model.RiskStrategyRecord;
import com.aurora.risk.base.service.IRiskStrategyRecordService;
import com.aurora.risk.service.StrategyRecordService;
import com.aurora.risk.strategy.StrategyLink;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author: Nick
 * @create: 2022-03-30 16:17
 **/
@Service
public class StrategyRecordServiceImpl implements StrategyRecordService {

    @Resource
    private IRiskStrategyRecordService strategyRecordService;

    @Override
    public RiskStrategyRecord initRiskStrategyRecord(String merOrderNo, String riskOrderNo, StrategyLink strategyLink) {
        RiskStrategyRecord record = new RiskStrategyRecord()
                .setMerOrderNo(merOrderNo)
                .setRiskOrderNo(riskOrderNo)
                .setStrategyId(strategyLink.getStrategyId())
                .setStrategyName(strategyLink.getStrategyName())
                .setStrategyNo(strategyLink.getStrategyNo())
                .setStrategyType(strategyLink.getStrategyType())
                .setStrategyVersion(strategyLink.getStrategyVersion())
                .setCreateTime(LocalDateTime.now());
        strategyRecordService.save(record);
        return record;
    }

    @Override
    public RiskStrategyRecord getRiskStrategyRecord(String riskOrderNo, String strategyNo) {
        return strategyRecordService.getOne(new QueryWrapper<RiskStrategyRecord>()
                .eq(RiskStrategyRecord.RISK_ORDER_NO, riskOrderNo)
                .eq(RiskStrategyRecord.STRATEGY_NO, strategyNo));
    }
}
