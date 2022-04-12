package com.aurora.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-28 15:13
 **/
@AllArgsConstructor
public enum CommonEnum {

    /**
     * 通用枚举
     */
    ENABLE("1", 1, "可用"),
    DISABLE("2", 2, "不可用"),
    DELETE("1", 1, "已删除"),
    NOT_DELETE("0", 0, "未删除"),

    ;

    public final String value;
    public final Integer intValue;
    public final String description;
}
