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
 * 规则
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规则唯一编号
     */
    @TableField("rule_no")
    private String ruleNo;

    /**
     * 规则姓名
     */
    @TableField("rule_name")
    private String ruleName;

    /**
     * 规则说明
     */
    @TableField("rule_desc")
    private String ruleDesc;

    /**
     * 规则状态，1启用，2禁用，3试用（执行但不参与决策）
     */
    @TableField("rule_status")
    private Integer ruleStatus;

    /**
     * 规则版本号
     */
    @TableField("rule_version")
    private Integer ruleVersion;

    /**
     * 子规则个数
     */
    @TableField("item_count")
    private Integer itemCount;

    /**
     * 父规则个数
     */
    @TableField("parent_count")
    private Integer parentCount;

    /**
     * 决策类型，result结果模式，score评分模式
     */
    @TableField("policy_type")
    private String policyType;

    /**
     * 数据获取类标识
     */
    @TableField("data_class")
    private String dataClass;

    /**
     * 数据获取方式（0同步,1异步）
     */
    @TableField("get_data_type")
    private Boolean getDataType;

    /**
     * 规则类型: 1=简单规则，2=复杂规则
     */
    @TableField("rule_type")
    private Integer ruleType;

    /**
     * 规则公式（简单规则/复杂规则评分模式：分数公式）
     */
    @TableField("rule_formula")
    private String ruleFormula;

    /**
     * 规则命中逻辑（复杂规则结果模式：1任意命中，2全部命中 )
     */
    @TableField("rule_logic")
    private Integer ruleLogic;

    /**
     * 规则命中结果（pass通过，reject拒绝，review复审，评分模式为分数）
     */
    @TableField("rule_hit_result")
    private String ruleHitResult;

    /**
     * 默认结果
     */
    @TableField("rule_defult_result")
    private String ruleDefultResult;

    /**
     * 当前应用策略数，默认0
     */
    @TableField("current_used_count")
    private Integer currentUsedCount;

    /**
     * 历史应用策略数
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

    public static final String RULE_NO = "rule_no";

    public static final String RULE_NAME = "rule_name";

    public static final String RULE_DESC = "rule_desc";

    public static final String RULE_STATUS = "rule_status";

    public static final String RULE_VERSION = "rule_version";

    public static final String ITEM_COUNT = "item_count";

    public static final String PARENT_COUNT = "parent_count";

    public static final String POLICY_TYPE = "policy_type";

    public static final String DATA_CLASS = "data_class";

    public static final String GET_DATA_TYPE = "get_data_type";

    public static final String RULE_TYPE = "rule_type";

    public static final String RULE_FORMULA = "rule_formula";

    public static final String RULE_LOGIC = "rule_logic";

    public static final String RULE_HIT_RESULT = "rule_hit_result";

    public static final String RULE_DEFULT_RESULT = "rule_defult_result";

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
