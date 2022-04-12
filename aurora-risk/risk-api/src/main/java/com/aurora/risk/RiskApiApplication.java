package com.aurora.risk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Nick
 * @create: 2022-03-10 09:12
 **/
@MapperScan({"com.aurora.risk.**.mapper"})
@EnableFeignClients
@SpringBootApplication
public class RiskApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RiskApiApplication.class, args);
    }
}
