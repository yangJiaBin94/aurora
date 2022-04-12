package com.aurora.calculation.context;

import com.aurora.common.request.calculation.GetIndexRequest;
import com.aurora.common.response.calculation.GetIndexResponse;

import java.util.Date;

/**
 * @author: Nick
 * @create: 2022-04-01 13:55
 **/
public interface CalculationContext {

    /**
     * 构建计算引擎上下文信息
     *
     * @param request : request
     * @return com.aurora.calculation.context.CalculationContextInfo
     * @author Nick
     * @date 2022/04/07
     */
    CalculationContextInfo buildContextInfo(GetIndexRequest request);

    /**
     * 执行
     *
     * @param contextInfo : contextInfo
     * @return void
     * @author Nick
     * @date 2022/04/07
     */
    void execute(CalculationContextInfo contextInfo);

    /**
     * 暂存或更新上下文信息到缓存
     *
     * @param contextInfo : contextInfo
     * @return void
     * @author Nick
     * @date 2022/04/08
     */
    void saveOrUpdateContext(CalculationContextInfo contextInfo);


    /**
     * 获取上下文
     *
     * @param riskOrderNo : riskOrderNo
     * @return com.aurora.calculation.context.CalculationContextInfo
     * @author Nick
     * @date 2022/04/08
    */
    CalculationContextInfo getCalculationContextInfo(String riskOrderNo);

    /**
     * 构建返回参数
     *
     * @param contextInfo : contextInfo
     * @return com.aurora.common.response.calculation.GetIndexResponse
     * @author Nick
     * @date 2022/04/06
     */
    GetIndexResponse buildResponse(CalculationContextInfo contextInfo);

    /**
     * 获取可用指标记录所在的最大时间点
     *
     * @param indexInfo : indexInfo
     * @return java.util.Date
     * @author Nick
     * @date 2022/04/11
     */
    Date getMaxRecalculateDate(IndexInfo indexInfo);

    /**
     * 保存指标记录
     *
     * @param contextInfo : contextInfo
     * @param indexInfo : indexInfo
     * @param isCalculation : isCalculation
     * @return void
     * @author Nick
     * @date 2022/04/12
     */
    void saveIndexRecord(CalculationContextInfo contextInfo, IndexInfo indexInfo, Boolean isCalculation);
}
