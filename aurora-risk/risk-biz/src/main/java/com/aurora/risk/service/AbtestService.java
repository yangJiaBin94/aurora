package com.aurora.risk.service;

import com.aurora.risk.base.model.RiskAbtest;

import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-29 16:04
 **/
public interface AbtestService {

    /**
     * 获取场景下Abtest
     *
     * @param sceneNo : sceneNo
     * @return java.util.List<com.aurora.risk.base.model.RiskAbtest>
     * @author Nick
     * @date 2022/03/29
    */
    List<RiskAbtest> getAbtestBySceneNo(String sceneNo);
}
