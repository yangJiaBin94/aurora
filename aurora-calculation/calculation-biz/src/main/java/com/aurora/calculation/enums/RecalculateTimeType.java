package com.aurora.calculation.enums;

/**
 * @author: Nick
 * @create: 2022-04-11 17:52
 **/
public enum RecalculateTimeType {

    /**
     * 指标计算间隔类型
     */
    //不重复计算
    NEVER,
    //总是重新计算
    ALWAYS,
    //秒
    SECONDS,
    //分
    MINUTES,
    //小时
    HOURS,
    //天
    DAYS,
    ;
}
