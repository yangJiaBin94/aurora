package com.aurora.risk.controller;

import com.aurora.common.request.risk.RiskVerifyRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.util.CommonUtil;
import com.aurora.risk.component.DistributedLock;
import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.entity.RiskResult;
import com.aurora.risk.feign.service.LoanApiCallBackService;
import com.aurora.risk.service.RiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-09 18:02
 **/
@Slf4j
@RestController
@RequestMapping("risk")
public class RiskController {

    private static final String LOG_PREFIX = LogPrefixConstant.RISK_CONTROLLER + "[RiskController] - ";

    @Resource
    private ThreadPoolTaskExecutor riskThreadPool;

    @Resource
    private LoanApiCallBackService loanApiCallBackService;

    @Resource
    private DistributedLock distributedLock;

    @Resource
    private RiskService riskService;

    @PostMapping("/verify/v1")
    public BaseResponse<RiskResult> verify(@RequestBody RiskVerifyRequest request) {
        RiskContextInfo riskContextInfo = CommonUtil.transferDate(request, RiskContextInfo.class);
        return BaseResponse.successResponse(riskService.risk(riskContextInfo));

    }
}
