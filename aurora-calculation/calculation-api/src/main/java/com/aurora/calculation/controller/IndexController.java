package com.aurora.calculation.controller;

import com.aurora.calculation.context.CalculationContext;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.service.IndexService;
import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.request.calculation.GetIndexRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.calculation.GetIndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-14 14:29
 **/
@Slf4j
@RestController
@RequestMapping("calculation/index")
public class IndexController {

    @Resource
    private IndexService indexService;

    @Resource
    private CalculationContext calculationContext;

    @PostMapping("getIndex")
    @ResponseBody
    public BaseResponse getIndex(@RequestBody GetIndexRequest request) {
        //构建上下文信息对象
        CalculationContextInfo contextInfo = calculationContext.buildContextInfo(request);
        //执行
        indexService.getIndex(contextInfo);
        //异步先返回对应code 执行完成后回调
        if (request.getIsAsync()) {
            return BaseResponse.getCommonResponse(ResponseEnum.CALCULATION_INDEX_1000);
        }
        GetIndexResponse response = calculationContext.buildResponse(contextInfo);
        return BaseResponse.successResponse(response);
    }
}
