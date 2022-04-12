package com.aurora.risk.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-29 11:43
 **/
@AllArgsConstructor
public enum RuleTypeEnum {

    //简单规则
    SIMPLE(1),
    //复杂规则
    COMPLEX(2),
    ;

    /**
     * 规则类型
     */
    public final Integer type;
}
