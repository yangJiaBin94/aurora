package com.aurora.risk.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-28 11:46
 **/
@AllArgsConstructor
public enum PolicyTypeEnum {

    /**
     * 规则决策类型
     */
    SCORE("score"),
    RESULT("result");

    /**
     * 决策类型
     */
    public final String type;
}
