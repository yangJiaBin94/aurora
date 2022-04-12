package com.aurora.risk.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-28 17:38
 **/
@AllArgsConstructor
public enum OrderStatusEnum {

    /**
     * 订单状态枚举
     */

    //初始化
    INIT(1),
    //进行中
    ON_GOING(2),
    //执行成功
    EXE_SUCCESS(3),
    //执行失败
    EXE_FAIL(4),
    //复审中
    //复审同步调用直接返回复审状态等待后续复审结果进行回调  异步则暂不回调，等待复审结果进行回调
    REVIEW(5),
    //复审完成
    REVIEW_OVER(6),
    //通知成功
    NOTICE_SUCCESS(7),
    //通知失败
    NOTICE_FAIL(8),
    //关闭
    CLOSE(9);

    /**
     * 订单状态
     */
    public final Integer status;
}
