package com.aurora.risk.feign.hystrix;

import com.aurora.common.request.calculation.GetIndexRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.calculation.GetIndexResponse;
import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.feign.service.CalculationIndexService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: Nick
 * @create: 2022-03-10 14:11
 **/
@Slf4j
@Component
public class CalculationIndexServiceFallbackFactory implements FallbackFactory<CalculationIndexService> {

    private static final String LOG_PREFIX = LogPrefixConstant.RISK_FEIGN_HYSTRIX + "[GetIndexServiceFallbackFactory] - ";

    @Override
    public CalculationIndexService create(Throwable cause) {
        log.error(LOG_PREFIX + "获取指标异常，服务熔断：{}", cause.getMessage());
        return new CalculationIndexService() {
            @Override
            public BaseResponse<GetIndexResponse> getIndex(GetIndexRequest request) {
                return null;
            }
        };
    }
}
