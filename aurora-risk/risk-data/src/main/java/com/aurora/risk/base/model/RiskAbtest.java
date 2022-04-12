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
 * ABTest
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskAbtest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ABTest名称
     */
    @TableField("abtest_name")
    private String abtestName;

    /**
     * 场景id
     */
    @TableField("scene_id")
    private Long sceneId;

    /**
     * 场景唯一编号
     */
    @TableField("scene_no")
    private String sceneNo;

    /**
     * 权重
     */
    @TableField("abtest_weight")
    private Integer abtestWeight;

    /**
     * 执行量限制类型；hour-时，day-日，week-周，month-月；默认day
     */
    @TableField("limit_type")
    private String limitType;

    /**
     * 执行量限制；默认0无上限
     */
    @TableField("limit_num")
    private Integer limitNum;

    /**
     * 是否主策略 0 否 1 是
     */
    @TableField("is_master")
    private Boolean isMaster;

    /**
     * 状态 1启用，2禁用
     */
    @TableField("abtest_status")
    private Integer abtestStatus;

    /**
     * 1正常可用，2时满限，3日满限，4周满限，5月满限
     */
    @TableField("timer_status")
    private Integer timerStatus;

    /**
     * ABTest排序
     */
    @TableField("abtest_order")
    private Integer abtestOrder;

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

    public static final String ABTEST_NAME = "abtest_name";

    public static final String SCENE_ID = "scene_id";

    public static final String SCENE_NO = "scene_no";

    public static final String ABTEST_WEIGHT = "abtest_weight";

    public static final String LIMIT_TYPE = "limit_type";

    public static final String LIMIT_NUM = "limit_num";

    public static final String IS_MASTER = "is_master";

    public static final String ABTEST_STATUS = "abtest_status";

    public static final String TIMER_STATUS = "timer_status";

    public static final String ABTEST_ORDER = "abtest_order";

    public static final String ADD_OPERATOR_ID = "add_operator_id";

    public static final String ADD_OPERATOR_NAME = "add_operator_name";

    public static final String UPDATE_OPERATOR_ID = "update_operator_id";

    public static final String UPDATE_OPERATOR_NAME = "update_operator_name";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_DELETE = "is_delete";

}
