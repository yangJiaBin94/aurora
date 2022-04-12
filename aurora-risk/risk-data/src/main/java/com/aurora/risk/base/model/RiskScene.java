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
 * 场景
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskScene implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 场景名称
     */
    @TableField("scene_name")
    private String sceneName;

    /**
     * 场景唯一编号
     */
    @TableField("scene_no")
    private String sceneNo;

    /**
     * 场景类型：冗余暂时不用
     */
    @TableField("scene_type")
    private String sceneType;

    /**
     * 场景描述
     */
    @TableField("scene_desc")
    private String sceneDesc;

    /**
     * 场景版本号
     */
    @TableField("scene_version")
    private Integer sceneVersion;

    /**
     * 当前包含策略数
     */
    @TableField("current_strategy_count")
    private Integer currentStrategyCount;

    /**
     * 历史包含策略数
     */
    @TableField("history_strategy_count")
    private Integer historyStrategyCount;

    /**
     * abtest数量
     */
    @TableField("abtest_count")
    private Integer abtestCount;

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

    public static final String SCENE_NAME = "scene_name";

    public static final String SCENE_NO = "scene_no";

    public static final String SCENE_TYPE = "scene_type";

    public static final String SCENE_DESC = "scene_desc";

    public static final String SCENE_VERSION = "scene_version";

    public static final String CURRENT_STRATEGY_COUNT = "current_strategy_count";

    public static final String HISTORY_STRATEGY_COUNT = "history_strategy_count";

    public static final String ABTEST_COUNT = "abtest_count";

    public static final String ADD_OPERATOR_ID = "add_operator_id";

    public static final String ADD_OPERATOR_NAME = "add_operator_name";

    public static final String UPDATE_OPERATOR_ID = "update_operator_id";

    public static final String UPDATE_OPERATOR_NAME = "update_operator_name";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_DELETE = "is_delete";

}
