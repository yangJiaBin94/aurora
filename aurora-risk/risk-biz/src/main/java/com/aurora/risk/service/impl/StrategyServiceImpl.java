package com.aurora.risk.service.impl;

import com.aurora.risk.base.model.RiskStrategy;
import com.aurora.risk.mapper.StrategyMapper;
import com.aurora.risk.service.StrategyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-29 15:05
 **/
@Service
public class StrategyServiceImpl implements StrategyService {

    @Resource
    private StrategyMapper strategyMapper;

    @Override
    public List<RiskStrategy> getRiskStrategyListBySceneNo(String sceneNo) {
        return strategyMapper.getRiskStrategyListBySceneNo(sceneNo);
    }

    @Override
    public List<RiskStrategy> getRiskStrategyListByAbtestId(Long abtestId) {
        return strategyMapper.getRiskStrategyListByAbtestId(abtestId);
    }
}
