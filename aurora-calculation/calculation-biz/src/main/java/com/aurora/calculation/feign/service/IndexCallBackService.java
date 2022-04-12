package com.aurora.calculation.feign.service;

import com.aurora.calculation.feign.service.hystrix.IndexCallBackServiceFallbackFactory;
import com.aurora.common.request.risk.ReceiveIndexRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.calculation.GetIndexResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: Nick
 * @create: 2022-03-10 13:59
 **/
@FeignClient(name = "gateway-server", fallbackFactory = IndexCallBackServiceFallbackFactory.class)
public interface IndexCallBackService {

    /**
     * 从计算引擎获取指标值
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
    */
    @PostMapping("risk/callBack/receiveIndex")
    BaseResponse<GetIndexResponse> receiveIndex(@RequestBody ReceiveIndexRequest request);


}
