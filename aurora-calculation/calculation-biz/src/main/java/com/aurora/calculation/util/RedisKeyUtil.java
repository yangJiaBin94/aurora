package com.aurora.calculation.util;

import com.aurora.calculation.constant.RedisConstant;
import com.aurora.calculation.context.CalculationContextInfo;

/**
 * @author: Nick
 * @create: 2022-04-07 14:38
 **/
public class RedisKeyUtil {

    /**
     * 获取指标缓存redis KEY
     *
     * @param contextInfo : contextInfo
     * @param className   : className
     * @return java.lang.String
     * @author Nick
     * @date 2022/04/08
     */
    public static String getIndexCacheRedisKey(CalculationContextInfo contextInfo, String className) {
        return RedisConstant.INDEX_CACHE + contextInfo.getMerchantNo() + contextInfo.getAppNo() + contextInfo.getPhoneNumber() + className;
    }

    /**
     * 获取上下文缓存redis KEY
     *
     * @param riskOrderNo : riskOrderNo
     * @return java.lang.String
     * @author Nick
     * @date 2022/04/08
     */
    public static String getContextCacheRedisKey(String riskOrderNo) {
        return RedisConstant.CONTEXT_CACHE + riskOrderNo;
    }
}
