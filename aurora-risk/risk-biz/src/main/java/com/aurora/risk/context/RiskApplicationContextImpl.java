package com.aurora.risk.context;

import com.alibaba.fastjson.JSONObject;
import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import com.aurora.common.request.loan.LoanApiCallBackRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.util.CommonUtil;
import com.aurora.risk.base.model.RiskAbtest;
import com.aurora.risk.base.model.RiskOrder;
import com.aurora.risk.base.model.RiskScene;
import com.aurora.risk.base.model.RiskStrategy;
import com.aurora.risk.base.service.IRiskOrderService;
import com.aurora.risk.constant.AbTestConstant;
import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.constant.RedisConstant;
import com.aurora.risk.entity.RiskResult;
import com.aurora.risk.enums.OrderStatusEnum;
import com.aurora.risk.feign.service.LoanApiCallBackService;
import com.aurora.risk.rule.AbstractRule;
import com.aurora.risk.service.*;
import com.aurora.risk.strategy.StrategyActuator;
import com.aurora.risk.strategy.StrategyLink;
import com.aurora.risk.util.BeanUtil;
import com.aurora.risk.util.WeightRandom;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Nick
 * @create: 2022-03-29 15:18
 **/
@Slf4j
@Service
public class RiskApplicationContextImpl implements RiskApplicationContext {

    private static final String LOG_PREFIX = LogPrefixConstant.RISK_CONTEXT + "[RiskApplicationContextImpl] - ";

    @Resource
    private OrderService orderService;

    @Resource
    private IRiskOrderService riskOrderService;

    @Resource
    private SceneService sceneService;

    @Resource
    private VerifyService verifyService;

    @Resource
    private StrategyService strategyService;

    @Resource
    private AbtestService abtestService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ThreadPoolTaskExecutor riskThreadPool;

    @Resource
    private StrategyActuator strategyActuator;

    @Resource
    private LoanApiCallBackService loanApiCallBackService;

    /**
     * 初始化订单
     */
    private void initOrder(RiskContextInfo riskContextInfo) {
        RiskScene scene = sceneService.getSceneBySceneNo(riskContextInfo.getSceneNo());
        RiskOrder order = new RiskOrder()
                .setAppNo(riskContextInfo.getAppNo())
                .setMerchantNo(riskContextInfo.getMerchantNo())
                .setCallbackUrl(riskContextInfo.getCallBackUrl())
                .setIsAsync(riskContextInfo.isAsync())
                .setMerOrderNo(riskContextInfo.getMerOrderNo())
                .setOrderStatus(OrderStatusEnum.INIT.status)
                .setRiskOrderNo(riskContextInfo.getRiskOrderNo())
                .setSceneNo(riskContextInfo.getSceneNo())
                .setSceneName(scene.getSceneName())
                .setSceneVersion(scene.getSceneVersion());

        riskOrderService.save(order);
    }

    /**
     * 构建策略链
     */
    private void buildStrategyLink(RiskContextInfo riskContextInfo) {
        List<RiskAbtest> abTestList = abtestService.getAbtestBySceneNo(riskContextInfo.getSceneNo());
        List<RiskStrategy> riskStrategyList = new ArrayList<>();
        //未配置Abtest 则直接取当前场景下所配置的策略
        if (CollectionUtils.isEmpty(abTestList)) {
            riskStrategyList = strategyService.getRiskStrategyListBySceneNo(riskContextInfo.getSceneNo());
        } else {
            //配置了Abtest的情况
            //过滤已到达限额的Abtest
            abTestList = abTestList.stream().filter(e -> {
                //0默认无限制
                if (e.getLimitNum() == 0) {
                    return true;
                }
                //获取Abtest限量redisKey
                String key = getAbtestLimitKey(e.getLimitType(), riskContextInfo);
                RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
                return atomicLong.get() < e.getLimitNum();
            }).collect(Collectors.toList());

            //根据权重选举Abtest
            WeightRandom<RiskAbtest> weightRandom = WeightRandom.create();
            abTestList.forEach(e -> {
                weightRandom.add(e, e.getAbtestWeight());
            });
            RiskAbtest abtest = weightRandom.getObject();
            //未选举到方案则使用主策略方案
            if (abtest == null) {
                List<RiskAbtest> collect = abTestList.stream().filter(RiskAbtest::getIsMaster).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(collect)) {
                    throw new BaseException(ResponseEnum.RISK_VERIFY_1004);
                }
                abtest = collect.get(0);
            }
            //若当前Abtest配置了限量 则更新执行量+1
            if (abtest.getLimitNum() > 0) {
                String key = getAbtestLimitKey(abtest.getLimitType(), riskContextInfo);
                RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
                atomicLong.incrementAndGet();
            }
            riskStrategyList = strategyService.getRiskStrategyListByAbtestId(abtest.getId());
        }
        if (CollectionUtils.isEmpty(riskStrategyList)) {
            throw new BaseException(ResponseEnum.RISK_VERIFY_1003);
        }
        riskContextInfo.setStrategyCount(riskStrategyList.size());
        riskContextInfo.setStrategyLink(new StrategyLink(riskStrategyList, 0));
    }

    /**
     * 获取Abtest限额Key
     *
     * @param limitType       : limitType
     * @param riskContextInfo : riskContextInfo
     * @return java.lang.String
     * @author Nick
     * @date 2022/03/29
     */
    private String getAbtestLimitKey(String limitType, RiskContextInfo riskContextInfo) {
        if (StringUtil.isEmpty(limitType)) {
            limitType = AbTestConstant.AB_TEST_LIMIT_TYPE_DEFAULT;
        }
        LocalDateTime currentDate = LocalDateTime.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        int hour = currentDate.getHour();
        WeekFields weekFields = WeekFields.ISO;
        int week = currentDate.get(weekFields.weekOfWeekBasedYear());

        String formatStr = "";
        switch (limitType) {
            case AbTestConstant.AB_TEST_LIMIT_TYPE_MONTH:
                formatStr = "Y" + year + "M" + month;
                break;
            case AbTestConstant.AB_TEST_LIMIT_TYPE_WEEK:
                formatStr = "Y" + year + "W" + week;
                break;
            case AbTestConstant.AB_TEST_LIMIT_TYPE_HOUR:
                formatStr = "Y" + year + "M" + month + "D" + day + "H" + hour;
                break;
            default:
                formatStr = "Y" + year + "M" + month + "D" + day;
        }
        return RedisConstant.AB_TEST_LIMIT_COUNT + ":" + riskContextInfo.getMerchantNo() + riskContextInfo.getAppNo() + riskContextInfo.getSceneNo() + ":" + formatStr;
    }

    /**
     * 构建策略结果集
     *
     * @param resultMap    : resultMap
     * @param strategyLink : strategyLink
     * @return void
     * @author Nick
     * @date 2022/03/30
     */
    private void buildStrategyResultInfoMap(Map<String, String> resultMap, StrategyLink strategyLink) {
        if (strategyLink != null) {
            resultMap.put(strategyLink.getStrategyName(), strategyLink.getResult());
            buildStrategyResultInfoMap(resultMap, strategyLink.getNext());
        }
    }

    private boolean hasAsyncRule(StrategyLink strategyLink) {
        AbstractRule ruleLink = strategyLink.getRuleLink();
        //若当前策略下规则链第一个规则为异步则直接返回true
        if (ruleLink.getGetDataType()) {
            return true;
        }
        //若当前策略下规则链第一个规则不是异步则递归判断此规则链的后续节点
        if (ruleLink.getNext() != null) {
            //查看规则链中是否有规则为异步规则 有则返回true
            if (hasAsyncRule(ruleLink)) {
                return true;
            }
            //当前规则链中无异步规则 则递归策略链查看其他策略下规则链
            if (strategyLink.getNext() != null) {
                return hasAsyncRule(strategyLink);
            }
        }
        return false;
    }

    /**
     * 查看规则链中是否有规则为异步规则
     *
     * @param ruleLink : ruleLink
     * @return boolean
     * @author Nick
     * @date 2022/04/06
     */
    private boolean hasAsyncRule(AbstractRule ruleLink) {
        //当前节点为异步规则则直接返回 true
        if (ruleLink.getGetDataType()) {
            return true;
        }
        //当前节点不是异步规则 则递归查看下一个节点
        if (ruleLink.getNext() != null) {
            return hasAsyncRule(ruleLink);
        }
        return false;
    }

    @Override
    public void init(RiskContextInfo riskContextInfo) {
        initOrder(riskContextInfo);
        buildStrategyLink(riskContextInfo);
    }

    @Override
    public void verifyAfter(RiskContextInfo riskContextInfo) {
        //异步规则不可配置在同步模式下（同步模式指同步的客户端请求）
        boolean isHasAsyncRule = hasAsyncRule(riskContextInfo.getStrategyLink());
        if (riskContextInfo.isAsync() && isHasAsyncRule) {
            throw new BaseException(ResponseEnum.RISK_RULE_1007);
        }
    }

    @Override
    public void verifyBefore(RiskContextInfo riskContextInfo) {
        //商户应用校验
        if (!verifyService.verifyMerchant(riskContextInfo.getMerchantNo())) {
            throw new BaseException(ResponseEnum.RISK_VERIFY_1001);
        }
        if (!verifyService.verifyApp(riskContextInfo.getAppNo())) {
            throw new BaseException(ResponseEnum.RISK_VERIFY_1002);
        }
        //场景校验
        if (!verifyService.verifySceneConfig(riskContextInfo.getAppNo(), riskContextInfo.getMerchantNo(), riskContextInfo.getSceneNo())) {
            throw new BaseException(ResponseEnum.RISK_VERIFY_1000);
        }
        if (ObjectUtils.isEmpty(sceneService.getSceneBySceneNo(riskContextInfo.getSceneNo()))) {
            throw new BaseException(ResponseEnum.RISK_VERIFY_1000);
        }

    }

    @Override
    public void execute(RiskContextInfo riskContextInfo) {
        //变更订单状态为执行中
        orderService.changeOrderStatus(OrderStatusEnum.ON_GOING, riskContextInfo.getRiskOrderNo());
        if (riskContextInfo.isAsync()) {
            //异步执行
            riskThreadPool.execute(() -> strategyActuator.executeStrategy(riskContextInfo));
        } else {
            //同步执行
            strategyActuator.executeStrategy(riskContextInfo);
        }
    }

    @Override
    public RiskResult buildRiskResult(RiskContextInfo riskContextInfo) {
        RiskResult riskResult = CommonUtil.transferDate(riskContextInfo, RiskResult.class);
        Map<String, String> resultMap = new HashMap<>(16);
        buildStrategyResultInfoMap(resultMap, riskContextInfo.getStrategyLink());
        riskResult.setResult(resultMap);
        riskResult.setResponseTime(LocalDateTime.now());
        return riskResult;
    }

    @Override
    public void saveRiskContextInfoIntoRedis(RiskContextInfo riskContextInfo) {
        //暂存riskContextInfo 上下文 等待回调继续执行
        RedissonClient redissonClient = BeanUtil.getBean(RedissonClient.class);
        String key = RedisConstant.RISK_CONTEXT + riskContextInfo.getRiskOrderNo();
        RBucket<RiskContextInfo> bucket = redissonClient.getBucket(key);
        bucket.setAsync(riskContextInfo);
    }

    @Override
    public RiskContextInfo getRiskContextInfoIntoRedis(String riskOrderNo) {
        String key = RedisConstant.RISK_CONTEXT + riskOrderNo;
        RBucket<RiskContextInfo> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public void checkAndCallBack(RiskContextInfo riskContextInfo) {
        //查看策略是否全部执行完成
        if (strategyActuator.checkAllStrategyIsDone(riskContextInfo.getStrategyLink())) {
            //若有策略结果为复审 则变更订单状态 不进行回调
            if (strategyActuator.hasReviewStrategy(riskContextInfo.getStrategyLink())) {
                //变更订单状态为待复审
                orderService.changeOrderStatus(OrderStatusEnum.REVIEW, riskContextInfo.getRiskOrderNo());
            } else {
                //变更订单状态为执行成功
                orderService.changeOrderStatus(OrderStatusEnum.EXE_SUCCESS, riskContextInfo.getRiskOrderNo());
                //清除风控上下文缓存
                clearContextCache(riskContextInfo);
                //回调客户端
                callBack(riskContextInfo);
            }
        }
    }

    @Override
    public void callBack(RiskContextInfo riskContextInfo) {
        RiskResult riskResult = buildRiskResult(riskContextInfo);
        BaseResponse response = loanApiCallBackService.loanApiCallBack(CommonUtil.transferDate(riskResult, LoanApiCallBackRequest.class));
        if (ResponseEnum.SUCCESS.getCode().equals(response.getCode())) {
            //变更订单状态为通知成功
            orderService.changeOrderStatus(OrderStatusEnum.NOTICE_SUCCESS, riskContextInfo.getRiskOrderNo());
            log.info(LOG_PREFIX + "回调通知客户端成功");
        } else {
            //变更订单状态为通知失败
            orderService.changeOrderStatus(OrderStatusEnum.NOTICE_FAIL, riskContextInfo.getRiskOrderNo());
            log.info(LOG_PREFIX + "回调通知客户端失败：{}", JSONObject.toJSONString(response));
        }
    }

    @Override
    public void clearContextCache(RiskContextInfo riskContextInfo) {
        //清除上下文缓存
        String key = RedisConstant.RISK_CONTEXT + riskContextInfo.getRiskOrderNo();
        RBucket<RiskContextInfo> bucket = redissonClient.getBucket(key);
        bucket.deleteAsync();
    }

}
