package com.aurora.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: Nick
 * @create: 2022-03-10 11:13
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class LoanApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanApiApplication.class, args);
    }
}
