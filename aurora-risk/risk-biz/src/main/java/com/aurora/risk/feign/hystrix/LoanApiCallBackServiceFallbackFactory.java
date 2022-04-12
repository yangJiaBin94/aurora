package com.aurora.risk.feign.hystrix;

import com.aurora.common.request.loan.LoanApiCallBackRequest;
import com.aurora.common.response.BaseResponse;

import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.feign.service.LoanApiCallBackService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: Nick
 * @create: 2022-03-10 14:11
 **/
@Slf4j
@Component
public class LoanApiCallBackServiceFallbackFactory implements FallbackFactory<LoanApiCallBackService> {

    private static final String LOG_PREFIX = LogPrefixConstant.RISK_FEIGN_HYSTRIX+"[LoanApiCallBackServiceFallbackFactory] - ";
    @Override
    public LoanApiCallBackService create(Throwable cause) {
        log.error(LOG_PREFIX+"回调通知loan-api异常，服务熔断：{}",cause.getMessage());
        return new LoanApiCallBackService() {
            @Override
            public BaseResponse loanApiCallBack(LoanApiCallBackRequest request) {
                return BaseResponse.failResponse();
            }
        };
    }
}
