package com.aurora.risk.rule;

import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import com.aurora.common.request.calculation.GetIndexRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.calculation.GetIndexResponse;
import com.aurora.common.util.CommonUtil;
import com.aurora.common.util.StringUtil;
import com.aurora.risk.component.DistributedLock;
import com.aurora.risk.constant.RedisConstant;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.enums.PolicyTypeEnum;
import com.aurora.risk.enums.RuleExecuteStatusEnum;
import com.aurora.risk.enums.RuleHitResultEnum;
import com.aurora.risk.feign.service.CalculationIndexService;
import com.aurora.risk.util.AviatorEvaluatorUtil;
import com.aurora.risk.util.BeanUtil;
import org.redisson.api.RLock;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 简单规则
 * @author: Nick
 * @create: 2022-03-28 10:03
 **/
public class SimpleRule extends AbstractRule {

    @Override
    void verify() {
        //TODO 校验简单规则
        if (StringUtil.isEmpty(this.getDataClass())) {
            throw new BaseException(ResponseEnum.RISK_RULE_1007);
        }

    }

    @Override
    void getIndexValue(RiskContextInfo riskContextInfo) {
        ConcurrentHashMap<String, String> indexCatch = riskContextInfo.getIndexCatch();
        //先从缓存获取
        String value = indexCatch.get(this.getDataClass());
        if (!StringUtil.isEmpty(value)) {
            this.setValue(value);
        } else {
            //未获取到缓存中的指标
            DistributedLock distributedLock = new DistributedLock();
            String key = riskContextInfo.getRiskOrderNo() + ":" + this.getDataClass();
            //上锁，持有锁LEASE_TIME秒未释放则自动释放，若未获取到锁则尝试获取WAIT_TIME时间
            RLock lock = distributedLock.tryLock(key, RedisConstant.GET_INDEX_LEASE_TIME, RedisConstant.GET_INDEX_WAIT_TIME);
            if (lock != null) {
                getIndex(riskContextInfo, indexCatch, distributedLock, lock);
            }
        }

    }

    private void getIndex(RiskContextInfo riskContextInfo, ConcurrentHashMap<String, String> indexCatch, DistributedLock distributedLock, RLock lock) {
        String value;
        //再次尝试从缓存获取指标值
        value = indexCatch.get(this.getDataClass());
        if (!StringUtil.isEmpty(value)) {
            this.setValue(value);
        } else {
            //若仍旧为null则获取
            //缓存没有则调用计算引擎计算指标
            CalculationIndexService calculationIndexService = BeanUtil.getBean(CalculationIndexService.class);
            GetIndexRequest request = CommonUtil.transferDate(riskContextInfo, GetIndexRequest.class);
            request.setCalculationType("client");
            request.setStrategyNo(this.getStrategyNo());
            request.setIndexClassNameList(Collections.singletonList(this.getDataClass()));
            request.setIsAsync(this.getGetDataType());
            BaseResponse<GetIndexResponse> getIndexResponseBaseResponse = calculationIndexService.getIndex(request);
            //如果当前指标进行了异步计算则设置规则执行状态为指标获取中
            String code = getIndexResponseBaseResponse.getCode();
            if (code.equals(ResponseEnum.CALCULATION_INDEX_1000.getCode())) {
                //解锁
                distributedLock.unLock(lock);
                this.setExecuteStatus(RuleExecuteStatusEnum.GETTING);
            } else if (code.equals(ResponseEnum.SUCCESS.getCode())) {
                //获取到了指标
                GetIndexResponse data = getIndexResponseBaseResponse.getData();
                if (ObjectUtils.isEmpty(data) || CollectionUtils.isEmpty(data.getIndexMap())) {
                    //解锁
                    distributedLock.unLock(lock);
                    throw new BaseException(ResponseEnum.RISK_RULE_1007);
                }
                //先将指标放入缓存
                Map<String, String> indexMap = data.getIndexMap();
                indexCatch.putAll(indexMap);
                //解锁
                distributedLock.unLock(lock);
                //装载规则value
                this.setValue(indexMap.get(this.getDataClass()));
            } else {
                //解锁
                distributedLock.unLock(lock);
                //其他情况均为获取失败
                throw new BaseException(ResponseEnum.RISK_RULE_1007);
            }
        }
    }

    @Override
    void getRuleResult() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("value", this.getValue());
        this.setIsHit(AviatorEvaluatorUtil.execute(this.getRuleFormula(), map));
        //如果命中则取规则命中结果
        if (this.getIsHit()) {
            this.setResult(this.getRuleHitResult());
        } else {
            //决策类型为结果模式 则取对应未命中状态
            if (PolicyTypeEnum.RESULT.type.equals(this.getPolicyType())) {
                String notHitStatus = RuleHitResultEnum.getNotHitStatus(this.getRuleHitResult());
                if (StringUtil.isEmpty(notHitStatus)) {
                    throw new BaseException(ResponseEnum.RISK_RULE_1007);
                }
                this.setResult(notHitStatus);
            } else {
                //决策模式为评分模式，未命中则0分
                this.setResult("0");
            }
        }
        //设置执行结果为完成
        this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
    }
}
