package com.aurora.common.request.risk;

import lombok.Data;

/**
 * @author: Nick
 * @create: 2022-03-10 17:51
 **/
@Data
public class RiskVerifyRequest {

    /**
     * 电话号码
     */
    private String phoneNumber;

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
     * 回调地址
     */
    private String callBackUrl;

    /**
     * 是否异步
     */
    private Boolean isAsync;

    /**
     * 场景编号
     */
    private String sceneNo;

    private String sign;

    private Long timestamp;

}
