package com.aurora.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: Nick
 * @create: 2022-03-10 10:58
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer02 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer02.class, args);
    }
}
