package com.aurora.calculation.index;

import com.aurora.calculation.annotation.Index;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.context.IndexInfo;
import com.aurora.calculation.enums.IndexExecuteStatusEnum;

/**
 * 非异步无分组指标
 *
 * @author: Nick
 * @create: 2022-03-14 15:37
 **/
@Index
public class IncomeIndexActuator extends AbstractIndexActuator {
    @Override
    public void calculation(CalculationContextInfo contextInfo) {
        IndexInfo indexInfo = this.getIndexInfo(contextInfo.getIndexInfoList());
        //TODO 这里根据指标逻辑进行指标计算
        //非异步指标则将值填充indexInfo并将执行状态置为DONE
        indexInfo.setStatusEnum(IndexExecuteStatusEnum.DONE);
        indexInfo.setValue("3000");
    }
}
