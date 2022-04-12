package com.aurora.loan.feign.service;

import com.aurora.common.response.BaseResponse;
import com.aurora.loan.feign.hystrix.RiskVerifyServiceFallbackFactory;
import com.aurora.common.request.risk.RiskVerifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: Nick
 * @create: 2022-03-10 13:59
 **/
@FeignClient(name = "gateway-server", fallbackFactory = RiskVerifyServiceFallbackFactory.class)
public interface RiskVerifyService {

    /**
     * 请求风控
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
    */
    @PostMapping("risk/verify/v1")
    BaseResponse riskVerify(@RequestBody RiskVerifyRequest request);
}
