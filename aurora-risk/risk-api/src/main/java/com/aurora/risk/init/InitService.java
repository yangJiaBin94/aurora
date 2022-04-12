package com.aurora.risk.init;

import com.aurora.risk.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-21 14:22
 **/
@Slf4j
@Component
public class InitService implements ApplicationRunner {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void run(ApplicationArguments args) {
        //初始化记录拒绝线程数量
        initRejectedCount();
    }

    public void initRejectedCount() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(RedisConstant.REJECTED_THREAD_COUNT);
        atomicLong.set(0);
    }
}
