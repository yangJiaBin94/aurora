package com.aurora.risk.service.impl;

import com.aurora.common.enums.CommonEnum;
import com.aurora.risk.base.model.RiskAppScene;
import com.aurora.risk.base.model.RiskScene;
import com.aurora.risk.base.service.IRiskAppSceneService;
import com.aurora.risk.base.service.IRiskSceneService;
import com.aurora.risk.service.SceneService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-29 10:03
 **/
@Service
@Slf4j
public class SceneServiceImpl implements SceneService {

    @Resource
    private IRiskSceneService sceneService;

    @Resource
    private IRiskAppSceneService appSceneService;

    @Override
    public RiskScene getSceneBySceneNo(String sceneNo) {

        return sceneService.getOne(new QueryWrapper<RiskScene>()
                .select(RiskScene.ID, RiskScene.SCENE_NO, RiskScene.SCENE_NAME, RiskScene.SCENE_VERSION)
                .eq(RiskScene.IS_DELETE, CommonEnum.NOT_DELETE.value)
                .eq(RiskScene.SCENE_NO, sceneNo));

    }

    @Override
    public boolean getStrategyIsAsync(String appNo, String sceneNo) {
        RiskAppScene riskAppScene = appSceneService.getOne(new QueryWrapper<RiskAppScene>()
                .select(RiskAppScene.IS_ASYNC)
                .eq(RiskAppScene.APP_NO, appNo)
                .eq(RiskAppScene.SCENE_NO, sceneNo));
        return riskAppScene.getIsAsync();
    }
}
