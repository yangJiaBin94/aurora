package com.aurora.risk.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-28 17:38
 **/
@AllArgsConstructor
public enum RuleExecuteStatusEnum {

    /**
     * 规则指执行状态
     */

    //获取指标中
    GETTING,
    //执行完成
    DONE,
    //未执行
    UN_EXECUTED,
    ;

}
