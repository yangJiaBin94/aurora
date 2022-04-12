package com.aurora.loan.controller;

import com.alibaba.fastjson.JSONObject;
import com.aurora.common.response.BaseResponse;
import com.aurora.loan.constant.LogPrefixConstant;
import com.aurora.loan.feign.service.RiskVerifyService;
import com.aurora.common.request.risk.RiskVerifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-10 18:48
 **/
@RestController
@Slf4j
@RequestMapping("loan/api/risk")
public class RiskVerifyController {

    private static final String LOG_PREFIX = LogPrefixConstant.LOAN_CONTROLLER + "[RiskVerifyController] - ";

    @Resource
    private RiskVerifyService riskVerifyService;

    @Resource
    private RedissonClient redissonClient;

    @PostMapping("/verify/v1")
    public BaseResponse riskCallBack() {
        RiskVerifyRequest riskVerifyRequest = new RiskVerifyRequest();
        riskVerifyRequest.setSign("sign11111");
        riskVerifyRequest.setTimestamp(System.currentTimeMillis());
        BaseResponse response = riskVerifyService.riskVerify(riskVerifyRequest);
        log.info(LOG_PREFIX + "请求risk-api 同步返回结果：{}", JSONObject.toJSONString(response));
        return BaseResponse.successResponse();
    }
}
