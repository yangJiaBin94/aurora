package com.aurora.risk.feign.service;

import com.aurora.common.request.calculation.GetIndexRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.calculation.GetIndexResponse;
import com.aurora.risk.feign.hystrix.CalculationIndexServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: Nick
 * @create: 2022-03-10 13:59
 **/
@FeignClient(contextId = "calculation", name = "gateway-server", fallbackFactory = CalculationIndexServiceFallbackFactory.class)
public interface CalculationIndexService {

    /**
     * 从计算引擎获取指标值
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
     */
    @PostMapping("calculation/index/getIndex")
    BaseResponse<GetIndexResponse> getIndex(@RequestBody GetIndexRequest request);


}
