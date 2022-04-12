package com.aurora.calculation.service;

import com.aurora.calculation.mongo.model.CalculationRecord;

/**
 * @author: Nick
 * @create: 2022-04-11 15:08
 **/
public interface CalculationRecordService {

    /**
     * 保存执行记录上下文
     *
     * @param record : record
     * @return void
     * @author Nick
     * @date 2022/04/12
    */
    void save(CalculationRecord record);
}
