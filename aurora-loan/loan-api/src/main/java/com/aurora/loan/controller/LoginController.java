package com.aurora.loan.controller;

import com.aurora.common.response.BaseResponse;
import com.aurora.loan.deom.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-11 18:15
 **/
@RestController
@Slf4j
@RequestMapping("loan/api/login")
public class LoginController {

    @PostMapping("/sendSms/v1")
    @ResponseBody
    public BaseResponse riskCallBack(@RequestBody String phone) {
        log.info("发送短信验证码，手机号：{}",phone);
        return BaseResponse.successResponse();
    }
}
