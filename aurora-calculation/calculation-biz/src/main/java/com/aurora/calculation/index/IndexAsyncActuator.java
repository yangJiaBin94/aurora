package com.aurora.calculation.index;

import com.alibaba.fastjson.JSONObject;
import com.aurora.calculation.context.CalculationContextInfo;

/**
 * 异步指标执行器接口
 *
 * @author: Nick
 * @create: 2022-04-07 15:46
 **/
public interface IndexAsyncActuator {

    /**
     * 异步指标回调后处理方法 计算指标并加载缓存
     *
     * @param contextInfo : contextInfo
     * @param data : data
     * @return void
     * @author Nick
     * @date 2022/04/07
    */
    void calculation(CalculationContextInfo contextInfo, JSONObject data);
}
