package com.aurora.risk.service.impl;

import com.aurora.common.enums.CommonEnum;
import com.aurora.risk.base.model.RiskApp;
import com.aurora.risk.base.model.RiskAppScene;
import com.aurora.risk.base.model.RiskMerchant;
import com.aurora.risk.base.service.IRiskAppSceneService;
import com.aurora.risk.base.service.IRiskAppService;
import com.aurora.risk.base.service.IRiskMerchantService;
import com.aurora.risk.service.VerifyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-29 10:41
 **/
@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {

    @Resource
    private IRiskAppSceneService riskAppSceneService;

    @Resource
    private IRiskAppService riskAppService;

    @Resource
    private IRiskMerchantService riskMerchantService;

    @Override
    public boolean verifyMerchant(String merchantNo) {
        return riskMerchantService.count(new QueryWrapper<RiskMerchant>()
                .eq(RiskMerchant.IS_DELETE, CommonEnum.NOT_DELETE.value)
                .eq(RiskMerchant.MERCHANT_STATUS, CommonEnum.ENABLE.value)
                .eq(RiskMerchant.MERCHANT_NO, merchantNo)) > 0;
    }

    @Override
    public boolean verifyApp(String appNo) {
        return riskAppService.count(new QueryWrapper<RiskApp>()
                .eq(RiskApp.IS_DELETE, CommonEnum.NOT_DELETE.value)
                .eq(RiskApp.APP_STATUS, CommonEnum.ENABLE.value)
                .eq(RiskApp.APP_NO, appNo)) > 0;
    }

    @Override
    public boolean verifySceneConfig(String appNo, String merchantNo, String sceneNo) {
        return riskAppSceneService.count(new QueryWrapper<RiskAppScene>()
                .eq(RiskAppScene.IS_DELETE, CommonEnum.NOT_DELETE.value)
                .eq(RiskAppScene.MERCHANT_NO, merchantNo)
                .eq(RiskAppScene.APP_NO, appNo)
                .eq(RiskAppScene.SCENE_STATUS, CommonEnum.ENABLE.value)
                .eq(RiskAppScene.SCENE_NO, sceneNo)) > 0;
    }
}
