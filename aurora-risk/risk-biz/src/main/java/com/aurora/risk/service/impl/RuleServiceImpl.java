package com.aurora.risk.service.impl;

import com.aurora.risk.base.model.RiskRule;
import com.aurora.risk.mapper.RuleMapper;
import com.aurora.risk.service.RuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-28 15:53
 **/
@Service
public class RuleServiceImpl implements RuleService {

    @Resource
    private RuleMapper ruleMapper;

    @Override
    public List<RiskRule> getSimpleRuleListByComplexRuleId(Long complexRuleId) {
        return ruleMapper.getSimpleRuleListByComplexRuleId(complexRuleId);
    }

    @Override
    public List<RiskRule> getRuleListByStrategyNo(String strategyNo) {
        return ruleMapper.getRuleListByStrategyNo(strategyNo);
    }
}
