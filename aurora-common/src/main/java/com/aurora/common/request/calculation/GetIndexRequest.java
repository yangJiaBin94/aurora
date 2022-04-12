package com.aurora.common.request.calculation;

import lombok.Data;

import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-14 15:26
 **/
@Data
public class GetIndexRequest {

    /**
     * 同步异步获取（指标类name集合中只要有一个异步指标，此字段则必须为true）
     */
    private Boolean isAsync;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 商户订单号
     */
    private String merOrderNo;

    /**
     * 风控订单号
     */
    private String riskOrderNo;

    /**
     * 应用编号
     */
    private String appNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 策略编号
     */
    private String strategyNo;

    /**
     * 计算类型（"job"定时任务 "client"客户端）
     */
    private String calculationType;

    /**
     * 指标类name集合
     */
    private List<String> indexClassNameList;
}
