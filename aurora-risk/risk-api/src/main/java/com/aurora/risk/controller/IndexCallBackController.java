package com.aurora.risk.controller;

import com.aurora.common.request.risk.ReceiveIndexRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.context.RiskApplicationContext;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.strategy.StrategyActuator;
import com.aurora.risk.strategy.StrategyLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Nick
 * @create: 2022-04-06 14:52
 **/
@Slf4j
@RestController
@RequestMapping("risk/callBack")
public class IndexCallBackController {

    private static final String LG_PREFIX = LogPrefixConstant.RISK_CONTROLLER + "[IndexCallBackController] - ";

    @Resource
    private RiskApplicationContext riskApplicationContext;

    @Resource
    private StrategyActuator strategyActuator;

    @PostMapping("/receiveIndex")
    @ResponseBody
    public BaseResponse receiveIndex(@RequestBody ReceiveIndexRequest request) {
        RiskContextInfo riskContextInfo = riskApplicationContext.getRiskContextInfoIntoRedis(request.getRiskOrderNo());
        //获取此指标规则所在的策略节点
        StrategyLink strategyNode = strategyActuator.getStrategyNode(riskContextInfo.getStrategyLink(), request.getStrategyNo());
        if (strategyNode == null) {
            log.error(LG_PREFIX + "当前回调指标接口中接收到的策略NO未在上下文中找到");
            return BaseResponse.failResponse();
        }
        //将指标结果放入缓存
        ConcurrentHashMap<String, String> indexCatch = riskContextInfo.getIndexCatch();
        indexCatch.putAll(request.getIndexMap());
        //重新执行指标所在策略节点的策略
        strategyActuator.executeOne(riskContextInfo, strategyNode);
        //所有策略是否执行完成 执行完成则发送通知（未执行完成情况为其策略中包含异步指标，异步指标结果暂时未获取到。等待回调）
        riskApplicationContext.checkAndCallBack(riskContextInfo);
        return BaseResponse.successResponse();
    }
}
