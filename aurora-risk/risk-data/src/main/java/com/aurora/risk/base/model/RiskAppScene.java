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
 * 产品场景关联表
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskAppScene implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户唯一编号
     */
    @TableField("merchant_no")
    private String merchantNo;

    /**
     * 产品唯一编号
     */
    @TableField("app_no")
    private String appNo;

    /**
     * 场景id
     */
    @TableField("scene_id")
    private Integer sceneId;

    /**
     * 场景唯一编号
     */
    @TableField("scene_no")
    private String sceneNo;

    /**
     * 场景状态，1启用，2禁用
     */
    @TableField("scene_status")
    private Integer sceneStatus;

    /**
     * 此场景下策略是否异步执行（1异步2同步）
     */
    @TableField("is_async")
    private Boolean isAsync;

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

    public static final String MERCHANT_NO = "merchant_no";

    public static final String APP_NO = "app_no";

    public static final String SCENE_ID = "scene_id";

    public static final String SCENE_NO = "scene_no";

    public static final String SCENE_STATUS = "scene_status";

    public static final String IS_ASYNC = "is_async";

    public static final String ADD_OPERATOR_ID = "add_operator_id";

    public static final String ADD_OPERATOR_NAME = "add_operator_name";

    public static final String UPDATE_OPERATOR_ID = "update_operator_id";

    public static final String UPDATE_OPERATOR_NAME = "update_operator_name";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_DELETE = "is_delete";

}
