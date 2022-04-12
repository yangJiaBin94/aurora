package com.aurora.risk.service.impl;

import com.aurora.common.enums.CommonEnum;
import com.aurora.risk.base.model.RiskAbtest;
import com.aurora.risk.base.service.IRiskAbtestService;
import com.aurora.risk.service.AbtestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-29 16:06
 **/
@Data
@Service
public class AbtestServiceImpl implements AbtestService {

    @Resource
    private IRiskAbtestService abtestService;

    @Override
    public List<RiskAbtest> getAbtestBySceneNo(String sceneNo) {
        return abtestService.list(new QueryWrapper<RiskAbtest>()
                .eq(RiskAbtest.IS_DELETE, CommonEnum.NOT_DELETE.value)
                .eq(RiskAbtest.TIMER_STATUS, CommonEnum.ENABLE.value)
                .eq(RiskAbtest.ABTEST_STATUS, CommonEnum.ENABLE.value)
                .eq(RiskAbtest.SCENE_NO, sceneNo)
        );
    }
}
