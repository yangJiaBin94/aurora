package com.aurora.risk.context;

import com.aurora.risk.entity.RiskResult;

/**
 * 风控流程上下文
 *
 * @author: Nick
 * @create: 2022-03-25 16:37
 **/

public interface RiskApplicationContext {

    /**
     * 初始化
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/03/29
     */
    void init(RiskContextInfo riskContextInfo);

    /**
     * 初始化前校验
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/03/29
     */
    void verifyBefore(RiskContextInfo riskContextInfo);

    /**
     * 初始化后校验（深度校验）
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/03/29
     */
    void verifyAfter(RiskContextInfo riskContextInfo);

    /**
     * 执行
     *
     * @param riskContextInfo : riskContextInfo
     * @author Nick
     * @date 2022/03/30
     */
    void execute(RiskContextInfo riskContextInfo);


    /**
     * 构建风控结果
     *
     * @param riskContextInfo : riskContextInfo
     * @return com.aurora.risk.entity.RiskResult
     * @author Nick
     * @date 2022/03/30
     */
    RiskResult buildRiskResult(RiskContextInfo riskContextInfo);

    /**
     * 暂存风控上下文信息到redis（保存 or 更新）
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/04/02
     */
    void saveRiskContextInfoIntoRedis(RiskContextInfo riskContextInfo);

    /**
     * 从redis中获取风控上下文信息
     *
     * @param riskOrderNo : riskOrderNo
     * @return RiskContextInfo
     * @author Nick
     * @date 2022/04/02
     */
    RiskContextInfo getRiskContextInfoIntoRedis(String riskOrderNo);

    /**
     * 异步策略执行检查策略是否全部执行完成若完成则回调客户端结果
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/04/06
     */
    void checkAndCallBack(RiskContextInfo riskContextInfo);

    /**
     * 回调客户端结果
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/04/06
     */
    void callBack(RiskContextInfo riskContextInfo);

    /**
     * 清除风控上下文缓存
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/04/06
    */
    void clearContextCache(RiskContextInfo riskContextInfo);
}
