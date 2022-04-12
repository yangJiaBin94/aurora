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
@Document(collection = "calculation_record")
public class CalculationRecord {

    @Id
    private String id;

    /**
     * 风控订单号
     */
    private String riskOrderNo;

    /**
     * 策略编号
     */
    private String strategyNo;

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
     * 上下文信息
     */
    private String context;

    /**
     * 创建时间
     */
    private Date createTime;


}
