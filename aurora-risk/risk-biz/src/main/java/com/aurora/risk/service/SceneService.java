package com.aurora.risk.service;

import com.aurora.risk.base.model.RiskScene;

/**
 * @author: Nick
 * @create: 2022-03-29 10:00
 **/
public interface SceneService {

    /**
     * 场景编号获取场景
     *
     * @param sceneNo : sceneNo
     * @return com.aurora.risk.base.model.RiskScene
     * @author Nick
     * @date 2022/03/29
     */
    RiskScene getSceneBySceneNo(String sceneNo);

    /**
     * 当前应用下场景配置策略是否异步执行
     *
     * @param appNo : appNo
     * @param sceneNo : sceneNo
     * @return boolean
     * @author Nick
     * @date 2022/03/30
    */
    boolean getStrategyIsAsync(String appNo, String sceneNo);


}
