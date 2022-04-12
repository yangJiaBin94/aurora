package com.aurora.risk.config;

import com.aurora.risk.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程拒绝策略
 *
 * @author: Nick
 * @create: 2022-03-09 18:02
 **/
@Slf4j
@Component
public class RiskRejectedExecutionHandler implements RejectedExecutionHandler {

    private static final String LOG_PREFIX = "[TQ] [TPP] - [TestRejectedExecutionHandler] - ";

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.warn(LOG_PREFIX + r.toString() + ": has been rejected.");
        RAtomicLong atomicLong = redissonClient.getAtomicLong(RedisConstant.REJECTED_THREAD_COUNT);
        atomicLong.incrementAndGet();
    }

}
