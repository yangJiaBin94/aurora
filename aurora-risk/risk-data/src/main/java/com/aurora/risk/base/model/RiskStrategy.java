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
 * 策略
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskStrategy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     *  策略类型：result决策，score评分
     */
    @TableField("strategy_type")
    private String strategyType;

    /**
     * 策略描述
     */
    @TableField("strategy_desc")
    private String strategyDesc;

    /**
     * 策略版本号
     */
    @TableField("strategy_version")
    private Integer strategyVersion;

    /**
     * 当前包含规则个数
     */
    @TableField("current_rule_count")
    private Integer currentRuleCount;

    /**
     * 历史包含规则数
     */
    @TableField("history_rule_count")
    private Integer historyRuleCount;

    /**
     * 当前应用场景数
     */
    @TableField("current_used_count")
    private Integer currentUsedCount;

    /**
     * 历史应用场景数
     */
    @TableField("history_used_count")
    private Integer historyUsedCount;

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

    public static final String STRATEGY_NAME = "strategy_name";

    public static final String STRATEGY_NO = "strategy_no";

    public static final String STRATEGY_TYPE = "strategy_type";

    public static final String STRATEGY_DESC = "strategy_desc";

    public static final String STRATEGY_VERSION = "strategy_version";

    public static final String CURRENT_RULE_COUNT = "current_rule_count";

    public static final String HISTORY_RULE_COUNT = "history_rule_count";

    public static final String CURRENT_USED_COUNT = "current_used_count";

    public static final String HISTORY_USED_COUNT = "history_used_count";

    public static final String ADD_OPERATOR_ID = "add_operator_id";

    public static final String ADD_OPERATOR_NAME = "add_operator_name";

    public static final String UPDATE_OPERATOR_ID = "update_operator_id";

    public static final String UPDATE_OPERATOR_NAME = "update_operator_name";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_DELETE = "is_delete";

}
