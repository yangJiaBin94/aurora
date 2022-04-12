package com.aurora.calculation.constant;

/**
 * @author: Nick
 * @create: 2022-03-21 14:20
 **/
public class RedisConstant {

    public static final String INDEX_CACHE= "CALCULATION:INDEX:CACHE:";
    public static final String CONTEXT_CACHE= "CALCULATION:CONTEXT:CACHE:";
    public static final String LOCK_KEY_PREFIX = "CALCULATION:LOCK:";
    public static final String REJECTED_THREAD_COUNT = "CALCULATION:REJECTED_THREAD_COUNT";
    public static final Long LEASE_TIME = 10L;
    public static final Long WAIT_TIME = 5L;
}
