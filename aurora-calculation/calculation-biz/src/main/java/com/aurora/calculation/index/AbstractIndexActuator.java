package com.aurora.calculation.index;

import com.aurora.calculation.annotation.Group;
import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.context.CalculationContext;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.context.IndexInfo;
import com.aurora.calculation.enums.IndexExecuteStatusEnum;
import com.aurora.calculation.enums.RecalculateTimeType;
import com.aurora.calculation.index.group.DefaultIndexGroupActuator;
import com.aurora.calculation.mongo.model.CalculationIndexRecord;
import com.aurora.calculation.service.CalculationIndexRecordService;
import com.aurora.calculation.util.BeanUtil;
import com.aurora.calculation.util.RedisKeyUtil;
import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: Nick
 * @create: 2022-03-31 16:32
 **/
@Slf4j
public abstract class AbstractIndexActuator implements IndexActuator {

    private static final String LOG_PREFIX = LogPrefixConstant.CALCULATION_INDEX + "[IndexAbstract] - ";

    @Override
    public void execute(CalculationContextInfo contextInfo) {
        IndexInfo indexInfo = getIndexInfo(contextInfo.getIndexInfoList());
        //只执行状态为未执行的指标
        if (!IndexExecuteStatusEnum.UN_EXECUTED.equals(indexInfo.getStatusEnum())) {
            return;
        }
        String value = tryToGetIndexValue(contextInfo, indexInfo.getStatusEnum());
        CalculationContext calculationContext = BeanUtil.getBean(CalculationContext.class);
        //若获取到值则存入indexInfo并将指标执行状态置为完成
        if (value != null) {
            indexInfo.setStatusEnum(IndexExecuteStatusEnum.DONE);
            indexInfo.setValue(value);
            calculationContext.saveIndexRecord(contextInfo, indexInfo, false);
            return;
        } else {
            //若此指标有执行组 却仍未获取到指标 则抛出异常
            if (!DefaultIndexGroupActuator.class.getName().equals(indexInfo.getGroupClassName())) {
                throw new BaseException(ResponseEnum.CALCULATION_INDEX_1001);
            }
        }
        //缓存和数据库中都未获取到值 则进行逻辑运算
        this.calculation(contextInfo);
        if (indexInfo.getStatusEnum().equals(IndexExecuteStatusEnum.DONE)) {
            calculationContext.saveIndexRecord(contextInfo, indexInfo, true);
        }
        //更新保存上下文信息到缓存
        calculationContext.saveOrUpdateContext(contextInfo);
    }


    /**
     * 计算指标
     *
     * @param contextInfo : contextInfo
     * @return void
     * @author Nick
     * @date 2022/04/11
     */
    @Override
    public abstract void calculation(CalculationContextInfo contextInfo);

    @Override
    public String verify(Map<? extends Class<?>, List<Class<?>>> indexGroupStructure, RMap<String, String> map) {
        return null;
    }

    /**
     * 尝试获取指标值
     *
     * @param contextInfo : contextInfo
     * @return void
     * @author Nick
     * @date 2022/04/07
     */
    public String tryToGetIndexValue(CalculationContextInfo contextInfo, IndexExecuteStatusEnum executeStatusEnum) {
        //已执行完成的指标不再获取
        if (IndexExecuteStatusEnum.DONE.equals(executeStatusEnum)) {
            return null;
        }
        //从缓存尝试获取
        String value = getValueFromCache(contextInfo);
        if (value == null) {
            //从数据库尝试获取
            value = getValueFromDb(contextInfo);
        }
        return value;
    }

    /**
     * 从上下文中获取当前指标所属信息indexInfo
     *
     * @param indexInfoList : indexInfoList
     * @return com.aurora.calculation.context.IndexInfo
     * @author Nick
     * @date 2022/04/07
     */
    public IndexInfo getIndexInfo(List<IndexInfo> indexInfoList) {
        List<IndexInfo> collect = indexInfoList.stream().filter(e -> e.getClassName().equals(this.getClass().getName())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return null;
        }
        return collect.get(0);
    }

    /**
     * 从缓存中获取指标值
     *
     * @param contextInfo : contextInfo
     * @return java.lang.String
     * @author Nick
     * @date 2022/04/07
     */
    public String getValueFromCache(CalculationContextInfo contextInfo) {
        RMap<String, String> map = getCacheMap(contextInfo);
        if (!CollectionUtils.isEmpty(map)) {
            return map.get(this.getClass().getName());
        }
        return null;
    }

    /**
     * 获取缓存map
     *
     * @param contextInfo : contextInfo
     * @return org.redisson.api.RMap<java.lang.String, java.lang.String>
     * @author Nick
     * @date 2022/04/08
     */
    @Override
    public RMap<String, String> getCacheMap(CalculationContextInfo contextInfo) {
        IndexInfo indexInfo = getIndexInfo(contextInfo.getIndexInfoList());
        RedissonClient redissonClient = BeanUtil.getBean(RedissonClient.class);
        String className = indexInfo.getGroupClassName();
        String key = RedisKeyUtil.getIndexCacheRedisKey(contextInfo, className);
        RMap<String, String> map = redissonClient.getMap(key);
        if (CollectionUtils.isEmpty(map)) {
            try {
                Group annotation = Class.forName(indexInfo.getGroupClassName()).getAnnotation(Group.class);
                map.expire(annotation.keepAlive(), TimeUnit.SECONDS);
            } catch (Exception e) {
                log.error(LOG_PREFIX + "初始化指标缓存map异常", e);
            }

        }
        return map;
    }

    /**
     * 从数据库中获取指标值（此主体相关指标可能以其他途径进行过计算 比如定时任务）
     *
     * @param contextInfo : contextInfo
     * @return java.lang.String
     * @author Nick
     * @date 2022/04/07
     */
    public String getValueFromDb(CalculationContextInfo contextInfo) {
        IndexInfo indexInfo = getIndexInfo(contextInfo.getIndexInfoList());
        //每次需要重新计算的指标不从数据库中获取
        if (RecalculateTimeType.ALWAYS.equals(indexInfo.getRecalculateTimeType())) {
            return null;
        }
        CalculationIndexRecordService calculationIndexRecordService = BeanUtil.getBean(CalculationIndexRecordService.class);
        CalculationContext calculationContext = BeanUtil.getBean(CalculationContext.class);
        CalculationIndexRecord record = calculationIndexRecordService.getCalculationIndexRecord(indexInfo.getClassName(), contextInfo.getMerchantNo(), contextInfo.getAppNo(), contextInfo.getPhoneNumber(), calculationContext.getMaxRecalculateDate(indexInfo));
        if (record != null) {
            return record.getValue();
        }
        return null;
    }


}
