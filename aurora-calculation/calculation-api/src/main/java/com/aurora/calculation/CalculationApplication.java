package com.aurora.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: Nick
 * @create: 2022-03-14 14:20
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CalculationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculationApplication.class, args);
    }
}
