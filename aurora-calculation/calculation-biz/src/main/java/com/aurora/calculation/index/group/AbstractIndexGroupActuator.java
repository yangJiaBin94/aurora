package com.aurora.calculation.index.group;

import com.aurora.calculation.annotation.Group;
import com.aurora.calculation.component.IndexGroupConfig;
import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.context.CalculationContext;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.enums.IndexExecuteStatusEnum;
import com.aurora.calculation.enums.RecalculateTimeType;
import com.aurora.calculation.index.IndexActuator;
import com.aurora.calculation.mongo.model.CalculationIndexRecord;
import com.aurora.calculation.service.CalculationIndexRecordService;
import com.aurora.calculation.util.BeanUtil;
import com.aurora.calculation.util.RedisKeyUtil;
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
 * @create: 2022-03-31 15:25
 **/
@Slf4j
public abstract class AbstractIndexGroupActuator implements IndexActuator {

    private static final String LOG_PREFIX = LogPrefixConstant.CALCULATION_INDEX_FACTORY + "[AbstractIndexGroupActuator] - ";

    @Override
    public void execute(CalculationContextInfo contextInfo) {
        RMap<String, String> map = this.getCacheMap(contextInfo);
        //缓存不为空则不进行计算
        if (!CollectionUtils.isEmpty(map)) {
            return;
        }
        //查看组内成员是否有GETTING状态的指标
        boolean hasGettingIndex = contextInfo.getIndexInfoList().stream().anyMatch(e -> e.getGroupClassName().equals(this.getClass().getName()) && e.getStatusEnum().equals(IndexExecuteStatusEnum.GETTING));
        if (hasGettingIndex) {
            //若指标依赖数据正在获取中则直接返回不进行后续逻辑，等待三方回调
            return;
        }
        //数据库中有指标记录，则直接拿来使用 不必重新计算
        if (getIndexFromDbAndPutIntoCache(contextInfo)) {
            return;
        }
        this.calculation(contextInfo);
        //校验
        String verify = this.verify(IndexGroupConfig.indexGroupStructure(), map);
        if (verify != null) {
            log.warn(LOG_PREFIX + "指标组：{},指标计算缓存缺失组内成员：{}", this.getClass().getName(), verify);
        }
        CalculationContext calculationContext = BeanUtil.getBean(CalculationContext.class);
        calculationContext.saveOrUpdateContext(contextInfo);
    }

    @Override
    public String verify(Map<? extends Class<?>, List<Class<?>>> indexGroupStructure, RMap<String, String> map) {
        //获取组内所有指标Class
        List<Class<?>> indexClassList = indexGroupStructure.get(this.getClass());
        //校验是否所有成员指标全部加载缓存
        List<String> cacheList = map.keySet().stream().sorted(String::compareTo).collect(Collectors.toList());
        List<String> configList = indexClassList.stream().map(Class::getName).sorted(String::compareTo).collect(Collectors.toList());
        if (!cacheList.toString().equals(configList.toString())) {
            return configList.stream().filter(e -> !cacheList.contains(e)).collect(Collectors.joining(","));
        }
        return null;
    }

    /**
     * 计算指标组所有指标并存入缓存
     *
     * @param contextInfo : contextInfo
     * @return java.lang.String
     * @author Nick
     * @date 2022/04/06
     */
    @Override
    public abstract void calculation(CalculationContextInfo contextInfo);

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
        RedissonClient redissonClient = BeanUtil.getBean(RedissonClient.class);
        String key = RedisKeyUtil.getIndexCacheRedisKey(contextInfo, this.getClass().getName());
        RMap<Object, Object> map = redissonClient.getMap(key);
        //空map则给到时效时间
        if (CollectionUtils.isEmpty(map)) {
            Group annotation = this.getClass().getAnnotation(Group.class);
            map.expire(annotation.keepAlive(), TimeUnit.SECONDS);
        }
        return redissonClient.getMap(key);
    }

    /**
     * 尝试从数据库获取指标
     * 这里可能返回false 但仍有部分指标被完结 所以后续执行计算若需要修改指标执行状态时先进行判断状态是否为DONE
     *
     * @param contextInfo : contextInfo
     * @return boolean
     * @author Nick
     * @date 2022/04/11
     */
    private boolean getIndexFromDbAndPutIntoCache(CalculationContextInfo contextInfo) {
        CalculationIndexRecordService calculationIndexRecordService = BeanUtil.getBean(CalculationIndexRecordService.class);
        CalculationContext calculationContext = BeanUtil.getBean(CalculationContext.class);
        //查看组内成员是否都在可复用指标最大时间范围内（是否全部不需要重新计算，都可以取上次计算的值直接使用）只要有一个不再则所有指标都重新计算
        boolean anyMatch = contextInfo.getIndexInfoList().stream().anyMatch(e -> {
            if (e.getGroupClassName().equals(this.getClass().getName())) {
                //每次需要重新计算的指标不从数据库中获取
                if (RecalculateTimeType.ALWAYS.equals(e.getRecalculateTimeType())) {
                    return true;
                }
                CalculationIndexRecord record = calculationIndexRecordService.getCalculationIndexRecord(e.getClassName(), contextInfo.getMerchantNo(), contextInfo.getAppNo(), contextInfo.getPhoneNumber(), calculationContext.getMaxRecalculateDate(e));
                if (record == null) {
                    return true;
                } else {
                    //若存在可复用记录则赋值并完结指标
                    e.setValue(record.getValue());
                    e.setStatusEnum(IndexExecuteStatusEnum.DONE);
                    //保存执行记录
                    calculationContext.saveIndexRecord(contextInfo, e, false);
                }
            }
            return false;
        });
        return !anyMatch;
    }

    /**
     * 变更组内成员执行状态（DONE状态成员不会回退状态）
     *
     * @param contextInfo       : contextInfo
     * @param executeStatusEnum : executeStatusEnum
     * @return void
     * @author Nick
     * @date 2022/04/12
     */
    public void changeGroupIndexExecuteStatus(CalculationContextInfo contextInfo, IndexExecuteStatusEnum executeStatusEnum) {
        contextInfo.getIndexInfoList().stream()
                .filter(e -> e.getGroupClassName().equals(this.getClass().getName()))
                .filter(e -> !e.getStatusEnum().equals(IndexExecuteStatusEnum.DONE))
                .forEach(e -> e.setStatusEnum(executeStatusEnum));
    }

}
