package com.aurora.risk.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 风控订单
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RiskOrder implements Serializable {

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
     * 是否异步：1异步，2同步
     */
    @TableField("is_async")
    private Boolean isAsync;

    /**
     * 异步回调地址
     */
    @TableField("callback_url")
    private String callbackUrl;

    /**
     * 状态：1初始化，2进行中，3执行成功，4执行失败，5复审中，6复审完成，7通知成功，8通知失败，9关闭
     */
    @TableField("order_status")
    private Integer orderStatus;

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
     * 场景版本
     */
    @TableField("scene_version")
    private Integer sceneVersion;

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

    public static final String MERCHANT_NO = "merchant_no";

    public static final String APP_NO = "app_no";

    public static final String MER_ORDER_NO = "mer_order_no";

    public static final String RISK_ORDER_NO = "risk_order_no";

    public static final String IS_ASYNC = "is_async";

    public static final String CALLBACK_URL = "callback_url";

    public static final String ORDER_STATUS = "order_status";

    public static final String SCENE_NAME = "scene_name";

    public static final String SCENE_NO = "scene_no";

    public static final String SCENE_VERSION = "scene_version";

    public static final String RUN_TIME = "run_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
