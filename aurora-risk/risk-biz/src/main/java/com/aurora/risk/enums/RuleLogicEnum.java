package com.aurora.risk.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-30 18:01
 **/
@AllArgsConstructor
public enum RuleLogicEnum {

    //任意命中
    ANY_ONE(1),
    //全部命中
    ALL(2),
    ;

    /**
     * 规则命中逻辑
     */
    public final Integer code;
}
