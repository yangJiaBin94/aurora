package com.aurora.calculation.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-28 17:38
 **/
@AllArgsConstructor
public enum IndexExecuteStatusEnum {

    /**
     * 指标指执行状态
     */

    //异步获取数据中
    GETTING,
    //执行完成
    DONE,
    //未执行
    UN_EXECUTED,
    ;

}
