package com.aurora.calculation.index;

import com.aurora.calculation.annotation.Index;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.index.group.TestAsyncGroupActuator;
import com.aurora.calculation.index.receive.TestIndexReceiver;

/**
 * 异步分组指标
 *
 * @author: Nick
 * @create: 2022-03-14 15:37
 **/
@Index(indexGroupActuator = TestAsyncGroupActuator.class, async = true, indexReceiver = TestIndexReceiver.class)
public class TestAsync02IndexActuator extends AbstractIndexActuator {
    @Override
    public void calculation(CalculationContextInfo contextInfo) {
    }
}
