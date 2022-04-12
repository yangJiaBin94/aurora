package com.aurora.risk.controller;

import com.aurora.common.response.ThreadPoolStatusResponse;
import com.aurora.risk.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-25 14:18
 **/
@RestController
@Slf4j
@RequestMapping("risk")
public class MonitorController {

    @Resource
    private ThreadPoolTaskExecutor executor;

    @Resource
    private RedissonClient redissonClient;

    @ResponseBody
    @RequestMapping(value = "/monitor/threadPoolStatus")
    public ThreadPoolStatusResponse threadPoolStatus() {
        return monitorThreadPool();
    }

    private ThreadPoolStatusResponse monitorThreadPool() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(RedisConstant.REJECTED_THREAD_COUNT);
        return new ThreadPoolStatusResponse(
                executor.getThreadPoolExecutor().getPoolSize(),
                executor.getThreadPoolExecutor().getCorePoolSize(),
                executor.getThreadPoolExecutor().getMaximumPoolSize(),
                executor.getThreadPoolExecutor().getActiveCount(),
                executor.getThreadPoolExecutor().getCompletedTaskCount(),
                executor.getThreadPoolExecutor().getTaskCount(),
                executor.getThreadPoolExecutor().isTerminated(),
                atomicLong.get());
    }
}
