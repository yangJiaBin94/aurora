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
 * 策略记录
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskStrategyRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户订单号
     */
    @TableField("mer_order_no")
    private String merOrderNo;

    /**
     * 风控订单号
     */
    @TableField("risk_order_no")
    private String riskOrderNo;

    /**
     * 策略id
     */
    @TableField("strategy_id")
    private Long strategyId;

    /**
     * 策略名称
     */
    @TableField("strategy_name")
    private String strategyName;

    /**
     * 策略唯一编号
     */
    @TableField("strategy_no")
    private String strategyNo;

    /**
     *  策略类型：decision决策，score评分
     */
    @TableField("strategy_type")
    private String strategyType;

    /**
     * 策略版本号
     */
    @TableField("strategy_version")
    private Integer strategyVersion;

    /**
     * 策略结果
     */
    @TableField("strategy_result")
    private String strategyResult;

    /**
     * 执行时间，单位ms毫秒
     */
    @TableField("run_time")
    private Long runTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


    public static final String ID = "id";

    public static final String MER_ORDER_NO = "mer_order_no";

    public static final String RISK_ORDER_NO = "risk_order_no";

    public static final String STRATEGY_ID = "strategy_id";

    public static final String STRATEGY_NAME = "strategy_name";

    public static final String STRATEGY_NO = "strategy_no";

    public static final String STRATEGY_TYPE = "strategy_type";

    public static final String STRATEGY_VERSION = "strategy_version";

    public static final String STRATEGY_RESULT = "strategy_result";

    public static final String RUN_TIME = "run_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
