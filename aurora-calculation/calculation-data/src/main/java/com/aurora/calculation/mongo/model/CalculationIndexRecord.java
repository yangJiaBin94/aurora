package com.aurora.calculation.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author: Nick
 * @create: 2022-04-11 15:09
 **/
@Data
@Document(collection = "calculation_index_record")
public class CalculationIndexRecord {

    @Id
    private String id;

    /**
     * 风控订单号
     */
    private String riskOrderNo;

    /**
     * 指标组类名
     */
    private String groupClassName;

    /**
     * 指标执行器类名
     */
    private String className;

    /**
     * 指标值
     */
    private String value;

    /**
     * 应用编号
     */
    private String appNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 计算类型（"job"定时任务 "client"客户端）
     */
    private String calculationType;

    /**
     * 是否计算所得（true计算出的值，false从之前记录获取）
     */
    private Boolean isCalculation;

    /**
     * 创建时间
     */
    private Date createTime;
}
