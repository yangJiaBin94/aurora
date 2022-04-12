package com.aurora.risk.strategy;

import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import com.aurora.common.util.StringUtil;
import com.aurora.risk.base.model.RiskStrategyRecord;
import com.aurora.risk.base.service.IRiskStrategyRecordService;
import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.context.RiskApplicationContext;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.enums.OrderStatusEnum;
import com.aurora.risk.enums.PolicyTypeEnum;
import com.aurora.risk.enums.RuleExecuteStatusEnum;
import com.aurora.risk.enums.RuleHitResultEnum;
import com.aurora.risk.rule.AbstractRule;
import com.aurora.risk.service.OrderService;
import com.aurora.risk.service.SceneService;
import com.aurora.risk.service.StrategyRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Nick
 * @create: 2022-03-30 13:44
 **/
@Slf4j
@Component
public class StrategyActuator {

    private static final String LG_PREFIX = LogPrefixConstant.RISK_ACTUATOR + "[StrategyActuator] - ";

    @Resource
    private StrategyRecordService strategyRecordService;

    @Resource
    private SceneService sceneService;

    @Resource
    private ThreadPoolTaskExecutor riskThreadPool;

    @Resource
    private RiskApplicationContext riskApplicationContext;

    @Resource
    private OrderService orderService;

    @Resource
    private IRiskStrategyRecordService iRiskStrategyRecordService;

    public void executeStrategy(RiskContextInfo riskContextInfo) {
        try {
            //异步执行策略
            if (sceneService.getStrategyIsAsync(riskContextInfo.getAppNo(), riskContextInfo.getSceneNo())) {
                //异步回调客户端
                if (riskContextInfo.isAsync()) {
                    executeAsync(riskContextInfo, riskContextInfo.getStrategyLink());
                } else {
                    //同步返回结果到客户端
                    CountDownLatch latch = new CountDownLatch(riskContextInfo.getStrategyCount());
                    executeAsync(riskContextInfo, riskContextInfo.getStrategyLink(), latch);
                    latch.await();
                }
            } else {
                executeSync(riskContextInfo, riskContextInfo.getStrategyLink());
                //异步回调客户端
                if (riskContextInfo.isAsync()) {
                    //所有策略是否执行完成 执行完成则发送通知（未执行完成情况为其策略中包含异步指标，异步指标结果暂时未获取到。等待回调）
                    riskApplicationContext.checkAndCallBack(riskContextInfo);
                }
            }
        } catch (Exception e) {
            log.error(LG_PREFIX + "策略执行异常");
            //变更订单状态为执行失败
            orderService.changeOrderStatus(OrderStatusEnum.EXE_FAIL, riskContextInfo.getRiskOrderNo());
            throw new BaseException(ResponseEnum.RISK_STRATEGY_1006);
        }
    }

    /**
     * 检查是否所有策略全部执行完成
     *
     * @param strategyLink : strategyLink
     * @return boolean
     * @author Nick
     * @date 2022/04/12
     */
    public boolean checkAllStrategyIsDone(StrategyLink strategyLink) {
        if (strategyLink == null) {
            return true;
        }
        if (StringUtil.isEmpty(strategyLink.getResult())) {
            return false;
        }
        return checkAllStrategyIsDone(strategyLink.getNext());
    }

    /**
     * 是否存在复审结果策略
     *
     * @param strategyLink : strategyLink
     * @return boolean
     * @author Nick
     * @date 2022/04/12
     */
    public boolean hasReviewStrategy(StrategyLink strategyLink) {
        if (strategyLink == null) {
            return false;
        }
        if (RuleHitResultEnum.REVIEW.hitStatus.equals(strategyLink.getResult())) {
            return true;
        }
        return hasReviewStrategy(strategyLink.getNext());
    }

    public StrategyLink getStrategyNode(StrategyLink strategyLink, String strategyNo) {
        if (strategyLink == null) {
            return null;
        }
        if (strategyLink.getStrategyNo().equals(strategyNo)) {
            return strategyLink;
        } else {
            return getStrategyNode(strategyLink.getNext(), strategyNo);
        }
    }

    /**
     * 只执行当前策略
     *
     * @param riskContextInfo : riskContextInfo
     * @param strategyNode    : strategyNode
     * @return void
     * @author Nick
     * @date 2022/04/02
     */
    public void executeOne(RiskContextInfo riskContextInfo, StrategyLink strategyNode) {
        doThis(riskContextInfo, strategyNode, null);
    }

    /**
     * 同步执行策略链（若其中有异步规则 不阻塞 继续执行下一个节点）
     *
     * @param riskContextInfo : riskContextInfo
     * @param strategyLink    : strategyLink
     * @return void
     * @author Nick
     * @date 2022/03/30
     */
    public void executeSync(RiskContextInfo riskContextInfo, StrategyLink strategyLink) {
        doThis(riskContextInfo, strategyLink, null);
        if (strategyLink.getNext() != null) {
            executeSync(riskContextInfo, strategyLink.getNext());
        }
    }

    /**
     * 从策略链中获取策略
     *
     * @param strategyLink : strategyLink
     * @param strategyNo   : strategyNo
     * @return com.aurora.risk.strategy.StrategyLink
     * @author Nick
     * @date 2022/04/02
     */
    public StrategyLink getStrategyInRiskContextInfo(StrategyLink strategyLink, String strategyNo) {
        if (strategyLink != null) {
            return strategyLink.getStrategyNo().equals(strategyNo) ? strategyLink : getStrategyInRiskContextInfo(strategyLink.getNext(), strategyNo);
        }
        return null;
    }

    /**
     * 异步执行策略链
     *
     * @param riskContextInfo : riskContextInfo
     * @param strategyLink    : strategyLink
     * @return void
     * @author Nick
     * @date 2022/03/30
     */
    public void executeAsync(RiskContextInfo riskContextInfo, StrategyLink strategyLink) {
        doNext(riskContextInfo, strategyLink);
        doThis(riskContextInfo, strategyLink, null);
    }

    /**
     * 异步执行策略链（同步返回客户端结果）
     *
     * @param riskContextInfo : riskContextInfo
     * @param strategyLink    : strategyLink
     * @return void
     * @author Nick
     * @date 2022/03/30
     */
    public void executeAsync(RiskContextInfo riskContextInfo, StrategyLink strategyLink, CountDownLatch latch) {
        doNext(riskContextInfo, strategyLink);
        doThis(riskContextInfo, strategyLink, latch);
    }

    private void doNext(RiskContextInfo riskContextInfo, StrategyLink strategyLink) {
        if (strategyLink.getNext() != null) {
            riskThreadPool.execute(() -> executeAsync(riskContextInfo, strategyLink.getNext()));
        }
    }

    private void doThis(RiskContextInfo riskContextInfo, StrategyLink strategyLink, CountDownLatch latch) {
        //获取当前策略执行记录
        RiskStrategyRecord riskStrategyRecord = strategyRecordService.getRiskStrategyRecord(riskContextInfo.getRiskOrderNo(), strategyLink.getStrategyNo());
        if (riskStrategyRecord == null) {
            //初始化策略执行记录
            riskStrategyRecord = strategyRecordService.initRiskStrategyRecord(riskContextInfo.getMerOrderNo(), riskContextInfo.getRiskOrderNo(), strategyLink);
        }
        //执行当前策略下规则链
        strategyLink.getRuleLink().execute(riskContextInfo);
        //检测是否所有规则都执行完毕
        if (checkRulesIsAllReady(strategyLink.getRuleLink())) {
            //构建策略结果
            buildStrategyResult(strategyLink);
            //保存策略执行记录
            riskStrategyRecord.setStrategyResult(strategyLink.getResult());
            Duration duration = java.time.Duration.between(riskStrategyRecord.getCreateTime(), LocalDateTime.now());
            riskStrategyRecord.setRunTime(duration.toMillis());
            iRiskStrategyRecordService.save(riskStrategyRecord);
            //若当前配置为异步执行策略，则当前策略执行完成后需根据请求是否异步进行后续处理 若策略为同步执行则不做任何处理
            if (sceneService.getStrategyIsAsync(riskContextInfo.getAppNo(), riskContextInfo.getSceneNo())) {
                //同步返回客户端结果
                if (!riskContextInfo.isAsync()) {
                    if (latch == null) {
                        log.error(LG_PREFIX + "同步通知客户端时，异步执行策略链，CountDownLatch null 异常！");
                    } else {
                        //更新上下文到redis
                        riskApplicationContext.saveRiskContextInfoIntoRedis(riskContextInfo);
                        latch.countDown();
                    }
                } else {
                    //异步策略检查是否全部执行完成 回调通知客户端结果
                    riskApplicationContext.checkAndCallBack(riskContextInfo);
                }
            }
        }
    }

    /**
     * 汇总规则结果为策略结果
     *
     * @param strategyLink : strategyLink
     * @return void
     * @author Nick
     * @date 2022/04/01
     */
    private void buildStrategyResult(StrategyLink strategyLink) {
        strategyLink.setResult(polymerizeRules(strategyLink.getRuleLink(), strategyLink.getStrategyType()));
    }

    /**
     * 检查是否所有规则执行完毕
     *
     * @param ruleLink : ruleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/01
     */
    private boolean checkRulesIsAllReady(AbstractRule ruleLink) {
        //如果为复审结果直接返回true
        if (!StringUtil.isEmpty(ruleLink.getResult()) && ruleLink.getResult().equals(RuleHitResultEnum.REVIEW.hitStatus)) {
            return true;
        }
        //非复审结果则查看执行状态 DONE则返回true 其他则false
        if (!RuleExecuteStatusEnum.DONE.equals(ruleLink.getExecuteStatus())) {
            //执行状态不为DONE 返回false
            return false;
        }
        //没有下一个节点则直接返回true
        if (ruleLink.getNext() == null) {
            return true;
        }
        //若当前节点规则执行状态为DONE 并且下一个节点不为null则递归检查下一个节点
        return checkRulesIsAllReady(ruleLink.getNext());
    }

    /**
     * 汇总规则结果为策略结果（结果模式：有任意规则结果为复审则策略结果为复审，全部规则结果为通过则策略结果为通过否则为拒绝。评分模式：分数相加求和）
     *
     * @param ruleLink     : ruleLink
     * @param strategyType : policyTypeEnum
     * @return java.lang.String
     * @author Nick
     * @date 2022/04/02
     */
    private String polymerizeRules(AbstractRule ruleLink, String strategyType) {
        //当前策略决策类型为结果模式时
        if (PolicyTypeEnum.RESULT.type.equals(strategyType)) {
            //为null则pass
            if (ruleLink == null) {
                return RuleHitResultEnum.PASS.hitStatus;
            }
            //结果为复审
            if (RuleHitResultEnum.REVIEW.hitStatus.equals(ruleLink.getResult())) {
                return RuleHitResultEnum.REVIEW.hitStatus;
            } else if (RuleHitResultEnum.PASS.hitStatus.equals(ruleLink.getResult())) {
                //结果为通过则检查下一个节点
                return polymerizeRules(ruleLink.getNext(), strategyType);
            } else {
                //拒绝则直接返回
                return RuleHitResultEnum.REJECT.hitStatus;
            }
        } else {
            if (ruleLink == null) {
                return "0";
            } else {
                return String.valueOf(Double.parseDouble(ruleLink.getResult()) + Double.parseDouble(polymerizeRules(ruleLink.getNext(), strategyType)));
            }
        }
    }
}
