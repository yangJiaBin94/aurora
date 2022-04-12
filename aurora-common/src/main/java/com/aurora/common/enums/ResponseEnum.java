package com.aurora.common.enums;


import lombok.Getter;

/**
 * @author: Nick
 * @create: 2022-03-14 15:26
 **/
@Getter
public enum ResponseEnum {

    /**
     * ResponseEnum
     */
    SUCCESS("000", "成功"),
    FAIL("100", "失败"),
    PARAMS_INVALID("101", "参数校验失败"),
    TOKEN_INVALID("102", "token无效, 请重新登录"),
    API_NOT_FOUND("103", "接口不存在"),
    AUTH_INVALID("104", "无权限"),
    ERROR("500", "服务器错误"),

    RISK_VERIFY_1000("RISK1000","场景配置异常"),
    RISK_VERIFY_1001("RISK1001","商户异常"),
    RISK_VERIFY_1002("RISK1002","应用异常"),
    RISK_VERIFY_1003("RISK1003","策略异常"),
    RISK_VERIFY_1004("RISK1004","Abtest无主策略方案"),
    RISK_ORDER_1005("RISK1005","订单状态变更异常"),
    RISK_STRATEGY_1006("RISK1006","策略执行异常"),
    RISK_RULE_1007("RISK1007","规则异常"),


    CALCULATION_INDEX_1000("CALCULATION1000","包含异步指标等待回调"),
    CALCULATION_INDEX_1001("CALCULATION1001","指标计算异常"),


    ;


    private String code;

    private String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

