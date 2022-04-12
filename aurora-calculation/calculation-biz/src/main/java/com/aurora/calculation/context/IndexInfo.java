package com.aurora.calculation.context;

import com.aurora.calculation.enums.IndexExecuteStatusEnum;
import com.aurora.calculation.enums.RecalculateTimeType;
import lombok.Data;

/**
 * @author: Nick
 * @create: 2022-04-07 16:14
 **/
@Data
public class IndexInfo {

    /**
     * 指标执行器类名
     */
    private String className;

    /**
     * 指标值
     */
    private String value;

    /**
     * 指标组类名
     */
    private String groupClassName;

    /**
     * 指标重新计算间隔时长
     */
    private Integer recalculateTime;

    /**
     * 指标重新计算间隔时间类型
     */
    private RecalculateTimeType recalculateTimeType;

    /**
     * 异步接收数据Api类名
     */
    private String receiveClassName;

    /**
     * 是否异步（默认否）
     */
    private Boolean isAsync = false;

    /**
     * 执行状态（默认未执行）
     */
    private IndexExecuteStatusEnum statusEnum = IndexExecuteStatusEnum.UN_EXECUTED;
}
