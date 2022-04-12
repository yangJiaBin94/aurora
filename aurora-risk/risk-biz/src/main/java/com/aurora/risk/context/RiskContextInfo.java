package com.aurora.risk.context;

import com.aurora.risk.constant.NumberPrefixConstant;
import com.aurora.risk.strategy.StrategyLink;
import com.aurora.risk.util.NumberUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 风控流程上下文
 *
 * @author: Nick
 * @create: 2022-03-25 16:37
 **/
@Data
public class RiskContextInfo {

    /**
     * 指标缓存
     */
    private ConcurrentHashMap<String,String> indexCatch = new ConcurrentHashMap<>();

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 应用编号
     */
    private String appNo;

    /**
     * 商户流水号
     */
    private String merOrderNo;

    /**
     * 回调地址
     */
    private String callBackUrl;

    /**
     * 是否异步
     */
    private boolean isAsync;

    /**
     * 场景编号
     */
    private String sceneNo;

    /**
     * 风控流水号
     */
    private String riskOrderNo = NumberUtil.getNumberByPrefix(NumberPrefixConstant.ORDER_NO_PREFIX);

    /**
     * 请求风控时间
     */
    private LocalDateTime requestTime = LocalDateTime.now();

    /**
     * 策略链
     */
    private volatile StrategyLink strategyLink;

    /**
     * 策略数量
     */
    private int strategyCount;


}
