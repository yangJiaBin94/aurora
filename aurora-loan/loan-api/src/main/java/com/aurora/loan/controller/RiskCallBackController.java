package com.aurora.loan.controller;

import com.alibaba.fastjson.JSON;
import com.aurora.common.response.BaseResponse;
import com.aurora.loan.constant.LogPrefixConstant;
import com.aurora.common.request.loan.LoanApiCallBackRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Nick
 * @create: 2022-03-10 11:46
 **/
@Slf4j
@RestController
@RequestMapping("loan/api/callBack")
public class RiskCallBackController {

    private static final String LOG_PREFIX = LogPrefixConstant.LOAN_CONTROLLER + "[RiskCallBackController] - ";

    @PostMapping("/riskCallBack/v1")
    public BaseResponse riskCallBack(@RequestBody LoanApiCallBackRequest request) {
        log.info(LOG_PREFIX + "risk-api 回调结果为：{}", JSON.toJSONString(request));
        return BaseResponse.successResponse();
    }

}
