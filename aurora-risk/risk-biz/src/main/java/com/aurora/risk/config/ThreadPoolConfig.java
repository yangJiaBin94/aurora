package com.aurora.risk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author: Nick
 * @create: 2022-03-09 18:02
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean("riskThreadPool")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2 * Runtime.getRuntime().availableProcessors() + 1);
        threadPoolTaskExecutor.setMaxPoolSize(1000);
        threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setThreadNamePrefix("risk-thread-pool");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new RiskRejectedExecutionHandler());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
