package com.aurora.calculation.service;


import com.aurora.calculation.context.CalculationContextInfo;

/**
 * @author: Nick
 * @create: 2022-03-14 14:37
 **/
public interface IndexService {

    /**
     * 获取指标
     *
     * @param contextInfo : contextInfo
     * @return java.util.concurrent.ConcurrentHashMap<java.lang.String,java.lang.String>
     * @author Nick
     * @date 2022/03/31
    */
    void getIndex(CalculationContextInfo contextInfo);


}
