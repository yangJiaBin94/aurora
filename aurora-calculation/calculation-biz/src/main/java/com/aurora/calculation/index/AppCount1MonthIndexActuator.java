package com.aurora.calculation.index;

import com.aurora.calculation.annotation.Index;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.index.group.AppCountGroupActuator;

/**
 * 非异步分组指标
 *
 * @author: Nick
 * @create: 2022-03-14 15:37
 **/
@Index(indexGroupActuator = AppCountGroupActuator.class)
public class AppCount1MonthIndexActuator extends AbstractIndexActuator {

    @Override
    public void calculation(CalculationContextInfo contextInfo) {
    }
}
