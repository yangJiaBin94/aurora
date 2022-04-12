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
 * 策略-规则关联
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskStrategyRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 策略id
     */
    @TableField("strategy_id")
    private Long strategyId;

    /**
     * 策略唯一编号
     */
    @TableField("strategy_no")
    private String strategyNo;

    /**
     * 规则id
     */
    @TableField("rule_id")
    private Long ruleId;

    /**
     * 规则唯一编号
     */
    @TableField("rule_no")
    private String ruleNo;

    /**
     * 规则顺序
     */
    @TableField("rule_order")
    private Integer ruleOrder;

    /**
     * 规则状态，1启用，2禁用，3试用（执行但不参与决策）
     */
    @TableField("rule_status")
    private Integer ruleStatus;

    /**
     * 添加人id
     */
    @TableField("add_operator_id")
    private Long addOperatorId;

    /**
     * 添加人姓名
     */
    @TableField("add_operator_name")
    private String addOperatorName;

    /**
     * 修改人id
     */
    @TableField("update_operator_id")
    private Long updateOperatorId;

    /**
     * 修改人姓名
     */
    @TableField("update_operator_name")
    private String updateOperatorName;

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

    /**
     * 是否删除 0 否 1 是
     */
    @TableField("is_delete")
    private Boolean isDelete;


    public static final String ID = "id";

    public static final String STRATEGY_ID = "strategy_id";

    public static final String STRATEGY_NO = "strategy_no";

    public static final String RULE_ID = "rule_id";

    public static final String RULE_NO = "rule_no";

    public static final String RULE_ORDER = "rule_order";

    public static final String RULE_STATUS = "rule_status";

    public static final String ADD_OPERATOR_ID = "add_operator_id";

    public static final String ADD_OPERATOR_NAME = "add_operator_name";

    public static final String UPDATE_OPERATOR_ID = "update_operator_id";

    public static final String UPDATE_OPERATOR_NAME = "update_operator_name";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_DELETE = "is_delete";

}
