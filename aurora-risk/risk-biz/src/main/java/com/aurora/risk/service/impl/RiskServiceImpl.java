package com.aurora.risk.service.impl;

import com.aurora.risk.context.RiskApplicationContext;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.entity.RiskResult;
import com.aurora.risk.service.RiskService;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-28 16:38
 **/
@Service
public class RiskServiceImpl implements RiskService {

    @Resource
    private RiskApplicationContext riskApplicationContext;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public RiskResult risk(RiskContextInfo riskContextInfo) {
        //初始化前校验
        riskApplicationContext.verifyBefore(riskContextInfo);
        //初始化
        riskApplicationContext.init(riskContextInfo);
        //初始化后校验
        riskApplicationContext.verifyAfter(riskContextInfo);
        //执行
        riskApplicationContext.execute(riskContextInfo);

        //异步模式返回null 等待执行完成后回调客户端
        if (riskContextInfo.isAsync()) {
            return null;
        } else {
            //清除风控上下文缓存
            riskApplicationContext.clearContextCache(riskContextInfo);
            //构建返回参数返回到客户端
            return riskApplicationContext.buildRiskResult(riskContextInfo);
        }
    }
}
