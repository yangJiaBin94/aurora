package com.aurora.calculation.index;

import com.alibaba.fastjson.JSONObject;
import com.aurora.calculation.annotation.Index;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.context.IndexInfo;
import com.aurora.calculation.enums.IndexExecuteStatusEnum;
import com.aurora.calculation.index.receive.TestIndexReceiver;
import org.redisson.api.RMap;

/**
 * 异步无分组指标
 *
 * @author: Nick
 * @create: 2022-03-14 15:37
 **/
@Index(async = true, indexReceiver = TestIndexReceiver.class)
public class TestAsync03IndexActuator extends AbstractIndexActuator implements IndexAsyncActuator {
    @Override
    public void calculation(CalculationContextInfo contextInfo) {
        IndexInfo indexInfo = this.getIndexInfo(contextInfo.getIndexInfoList());

        //TODO 指标未获取到则尝试从数据库中获取三方调用记录中三方返回数据（查看此主体是否获取过此三方的数据）
        JSONObject data = new JSONObject();
        //若查到了此主体调用此三方的调用记录并获取到了返回数据 则不必再次请求三方 直接拿数据进行计算
        if (data != null) {
            calculation(contextInfo, data);
            return;
        }
        //缓存和数据库中都未获取到值则发起三方请求
        //TODO 初始化三方调用记录
        //TODO 调用三方服务（加锁保证同一主体同一三方只调用一次）
        //TODO 更新调用记录状态

        indexInfo.setStatusEnum(IndexExecuteStatusEnum.GETTING);
    }

    @Override
    public void calculation(CalculationContextInfo contextInfo, JSONObject data) {
        IndexInfo indexInfo = this.getIndexInfo(contextInfo.getIndexInfoList());
        //TODO 这里根据指标逻辑进行指标计算 并存入数据库
        //获取缓存map 将计算出的指标值存入缓存
        RMap<String, String> map = this.getCacheMap(contextInfo);
        map.put(this.getClass().getName(), "03");
        //将指标执行状态置为未执行
        indexInfo.setStatusEnum(IndexExecuteStatusEnum.UN_EXECUTED);
    }
}
