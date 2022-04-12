package com.aurora.calculation.feign.service.hystrix;

import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.feign.service.IndexCallBackService;
import com.aurora.common.request.risk.ReceiveIndexRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.calculation.GetIndexResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: Nick
 * @create: 2022-03-10 14:11
 **/
@Slf4j
@Component
public class IndexCallBackServiceFallbackFactory implements FallbackFactory<IndexCallBackService> {

    private static final String LOG_PREFIX = LogPrefixConstant.CALCULATION_FEIGN_HYSTRIX + "[IndexCallBackServiceFallbackFactory] - ";

    @Override
    public IndexCallBackService create(Throwable cause) {
        log.error(LOG_PREFIX + "获取指标异常，服务熔断：{}", cause.getMessage());
        return new IndexCallBackService() {
            @Override
            public BaseResponse<GetIndexResponse> receiveIndex(ReceiveIndexRequest request) {
                return null;
            }
        };
    }
}
