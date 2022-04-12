package com.aurora.calculation.index.group;

import com.aurora.calculation.annotation.Group;
import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.index.AppCount1MonthIndexActuator;
import com.aurora.calculation.index.AppCountSumIndexActuator;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;

/**
 * 非异步分组执行器
 *
 * @author: Nick
 * @create: 2022-03-31 11:18
 **/
@Slf4j
@Group
public class AppCountGroupActuator extends AbstractIndexGroupActuator {

    private static final String LOG_PREFIX = LogPrefixConstant.CALCULATION_INDEX_FACTORY + "[AppCountGroupHandler] - ";

    @Override
    public void calculation(CalculationContextInfo contextInfo) {
        RMap<String, String> map = this.getCacheMap(contextInfo);

        try {
            //TODO 计算分组内指标值 放入缓存 缓存默认60秒
            map.put(AppCountSumIndexActuator.class.getName(), "200");
            map.put(AppCount1MonthIndexActuator.class.getName(), "100");
        } catch (Exception e) {
            log.error(LOG_PREFIX + "指标组：{}执行异常", this.getClass().getName(), e);
        }
    }
}
