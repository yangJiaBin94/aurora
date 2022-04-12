package com.aurora.risk.component;

import com.aurora.risk.constant.RedisConstant;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: Nick
 * @create: 2022-03-25 18:36
 **/
@Component
public class DistributedLock {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取锁
     *
     * @param key       key
     * @param leaseTime 锁租赁时间
     * @param waitTime  等待获取锁时间
     * @return 锁
     */
    public RLock tryLock(String key, long leaseTime, long waitTime) {
        try {
            RLock lock = redissonClient.getLock(RedisConstant.LOCK_KEY_PREFIX + key);
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS) ? lock : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * 释放锁
     *
     * @param lock 锁
     */
    public void unLock(Object lock) {
        try {
            if (lock != null) {
                ((RLock) lock).unlock();
            }
        } catch (Exception ignored) {
        }
    }
}
