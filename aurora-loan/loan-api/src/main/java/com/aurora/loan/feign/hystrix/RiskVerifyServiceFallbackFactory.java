package com.aurora.loan.feign.hystrix;

import com.aurora.common.response.BaseResponse;
import com.aurora.loan.constant.LogPrefixConstant;
import com.aurora.loan.feign.service.RiskVerifyService;
import com.aurora.common.request.risk.RiskVerifyRequest;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: Nick
 * @create: 2022-03-10 14:11
 **/
@Slf4j
@Component
public class RiskVerifyServiceFallbackFactory implements FallbackFactory<RiskVerifyService> {

    private static final String LOG_PREFIX = LogPrefixConstant.LOAN_FEIGN_HYSTRIX + "[RiskVerifyServiceFallbackFactory] - ";

    @Override
    public RiskVerifyService create(Throwable cause) {
        log.error(LOG_PREFIX + "请求风控API异常：{}", cause.getMessage());
        return new RiskVerifyService() {
            @Override
            public BaseResponse riskVerify(RiskVerifyRequest request) {
                return  BaseResponse.failResponse("熔断");
            }
        };
    }
}
