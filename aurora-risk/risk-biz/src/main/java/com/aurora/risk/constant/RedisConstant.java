package com.aurora.risk.constant;

/**
 * @author: Nick
 * @create: 2022-03-21 14:20
 **/
public class RedisConstant {

    public static final String REJECTED_THREAD_COUNT = "RISK:REJECTED_THREAD_COUNT:";
    public static final String AB_TEST_LIMIT_COUNT = "RISK:AB_TEST_LIMIT_COUNT:";
    public static final String LOCK_KEY_PREFIX = "RISK:LOCK:";
    public static final String STRATEGY_COUNT_LATCH = "RISK:STRATEGY_COUNT_LATCH:";
    public static final String RISK_CONTEXT = "RISK:CONTEXT:";
    public static final Long GET_INDEX_LEASE_TIME = 5L;
    public static final Long GET_INDEX_WAIT_TIME = 6L;
}
