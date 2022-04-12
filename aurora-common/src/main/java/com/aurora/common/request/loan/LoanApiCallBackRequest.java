package com.aurora.common.request.loan;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author: Nick
 * @create: 2022-03-10 18:21
 **/
@Data
public class LoanApiCallBackRequest {

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 应用编号
     */
    private String appNo;

    /**
     * 商户流水号
     */
    private String merOrderNo;

    /**
     * 是否异步
     */
    private boolean isAsync;

    /**
     * 场景编号
     */
    private String sceneNo;

    /**
     * 风控流水号
     */
    private String riskOrderNo;

    /**
     * 请求风控时间
     */
    private LocalDateTime requestTime;

    /**
     * 通知时间
     */
    private LocalDateTime responseTime;

    /**
     * 策略结果 key：策略名称
     */
    private Map<String, String> result;

}
