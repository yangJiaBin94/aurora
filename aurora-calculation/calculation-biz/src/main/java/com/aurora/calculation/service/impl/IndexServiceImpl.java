package com.aurora.calculation.service.impl;

import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.context.CalculationContext;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-14 14:45
 **/
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    private static final String LOG_PREFIX = LogPrefixConstant.CALCULATION_SERVICE + "[IndxServiceImpl] - ";

    @Resource
    private CalculationContext calculationContext;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void getIndex(CalculationContextInfo contextInfo) {
        //执行
        calculationContext.execute(contextInfo);

    }
}
