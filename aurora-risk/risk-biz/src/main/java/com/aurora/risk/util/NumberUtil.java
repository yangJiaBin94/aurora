package com.aurora.risk.util;

import com.aurora.common.util.SnowflakeIdWorker;

/**
 * @author: Nick
 * @create: 2022-03-28 18:21
 **/
public class NumberUtil {

    private static final SnowflakeIdWorker ID_WORKER = new SnowflakeIdWorker(1);

    /**
     * 根据前缀获取编号
     *
     * @param prefix : prefix
     * @return java.lang.String
     * @author Nick
     * @date 2022/03/28
    */
    public static String getNumberByPrefix(String prefix) {
        return prefix + ID_WORKER.nextId();
    }
}
