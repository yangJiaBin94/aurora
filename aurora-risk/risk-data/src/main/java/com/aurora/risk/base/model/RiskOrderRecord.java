package com.aurora.risk.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 风控订单状态变更记录
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskOrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 风控订单号
     */
    @TableField("risk_order_no")
    private String riskOrderNo;

    /**
     * 前状态：1初始化，2进行中，3执行成功，4执行失败，5复审中，6复审完成，7通知成功，8通知失败，9关闭
     */
    @TableField("last_status")
    private Integer lastStatus;

    /**
     * 当前状态：1初始化，2进行中，3执行成功，4执行失败，5复审中，6复审完成，7通知成功，8通知失败，9关闭
     */
    @TableField("now_status")
    private Integer nowStatus;

    /**
     * 耗时，单位ms毫秒
     */
    @TableField("run_time")
    private Long runTime;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


    public static final String ID = "id";

    public static final String RISK_ORDER_NO = "risk_order_no";

    public static final String LAST_STATUS = "last_status";

    public static final String NOW_STATUS = "now_status";

    public static final String RUN_TIME = "run_time";

    public static final String CREATE_TIME = "create_time";

}
