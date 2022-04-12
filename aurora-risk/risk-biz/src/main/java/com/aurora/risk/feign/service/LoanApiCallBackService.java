package com.aurora.risk.feign.service;

import com.aurora.common.request.loan.LoanApiCallBackRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.risk.feign.hystrix.LoanApiCallBackServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: Nick
 * @create: 2022-03-10 13:59
 **/
@FeignClient(name = "gateway-server", fallbackFactory = LoanApiCallBackServiceFallbackFactory.class)
public interface LoanApiCallBackService {

    /**
     * 回调通知loan-api 结果
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
    */
    @PostMapping("loan/api/callBack/riskCallBack/v1")
    BaseResponse loanApiCallBack(@RequestBody LoanApiCallBackRequest request);
}
