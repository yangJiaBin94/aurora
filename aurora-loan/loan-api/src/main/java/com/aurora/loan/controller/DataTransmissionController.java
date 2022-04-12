package com.aurora.loan.controller;

import com.aurora.common.response.BaseResponse;
import com.aurora.loan.deom.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-11 18:15
 **/
@RestController
@Slf4j
@RequestMapping("loan/api/data")
public class DataTransmissionController {

    @Resource
    private KafkaProducer kafkaProducer;

    @PostMapping("/test/v1")
    public BaseResponse riskCallBack() {
        kafkaProducer.send("测试kafka消息");
        return BaseResponse.successResponse();
    }
}
