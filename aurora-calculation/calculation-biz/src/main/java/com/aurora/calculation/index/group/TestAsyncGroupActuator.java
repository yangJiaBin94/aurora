package com.aurora.calculation.index.group;

import com.alibaba.fastjson.JSONObject;
import com.aurora.calculation.annotation.Group;
import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.enums.IndexExecuteStatusEnum;
import com.aurora.calculation.index.IndexAsyncActuator;
import com.aurora.calculation.index.TestAsync01IndexActuator;
import com.aurora.calculation.index.TestAsync02IndexActuator;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;

/**
 * 异步分组执行器
 *
 * @author: Nick
 * @create: 2022-03-31 11:18
 **/
@Slf4j
@Group
public class TestAsyncGroupActuator extends AbstractIndexGroupActuator implements IndexAsyncActuator {

    private static final String LOG_PREFIX = LogPrefixConstant.CALCULATION_INDEX_FACTORY + "[TestAsyncGroupHandler] - ";

    @Override
    public void calculation(CalculationContextInfo contextInfo) {

        //TODO 指标未获取到则尝试从数据库中获取三方调用记录中三方返回数据（查看此主体是否获取过此三方的数据）
        JSONObject data = new JSONObject();
        //若查到了此主体调用此三方的调用记录并获取到了返回数据 则不必再次请求三方 直接拿数据进行计算
        if (data != null) {
            calculation(contextInfo, data);
            return;
        }
        //TODO 初始化三方调用记录
        //TODO 调用三方服务（加锁保证同一主体同一三方只调用一次）
        //TODO 更新调用记录状态
        //将其组内所有指标执行状态置为异步数据获取中 GETTING
        this.changeGroupIndexExecuteStatus(contextInfo, IndexExecuteStatusEnum.GETTING);

    }

    @Override
    public void calculation(CalculationContextInfo contextInfo, JSONObject data) {
        //TODO 计算指标 并存入数据库
        //放入缓存
        RMap<String, String> map = this.getCacheMap(contextInfo);
        map.put(TestAsync01IndexActuator.class.getName(), "01");
        map.put(TestAsync02IndexActuator.class.getName(), "02");
        //将其组内所有指标执行状态置为未执行
        this.changeGroupIndexExecuteStatus(contextInfo, IndexExecuteStatusEnum.UN_EXECUTED);
    }
}
