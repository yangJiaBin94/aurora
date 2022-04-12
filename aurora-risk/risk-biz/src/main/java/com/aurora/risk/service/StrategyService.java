package com.aurora.risk.service;

import com.aurora.risk.base.model.RiskStrategy;

import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-29 15:03
 **/
public interface StrategyService {

    /**
     * 获取场景下策略集合
     *
     * @param sceneNo : sceneNo
     * @return java.util.List<com.aurora.risk.base.model.RiskStrategy>
     * @author Nick
     * @date 2022/03/29
    */
    List<RiskStrategy> getRiskStrategyListBySceneNo(String sceneNo);

    /**
     * 获取Abtest下策略集合
     *
     * @param abtestId : abtestId
     * @return java.util.List<com.aurora.risk.base.model.RiskStrategy>
     * @author Nick
     * @date 2022/03/29
    */
    List<RiskStrategy> getRiskStrategyListByAbtestId(Long abtestId);
}
