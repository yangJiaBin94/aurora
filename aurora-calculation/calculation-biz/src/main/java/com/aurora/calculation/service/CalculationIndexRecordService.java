package com.aurora.calculation.service;

import com.aurora.calculation.mongo.model.CalculationIndexRecord;

import java.util.Date;

/**
 * @author: Nick
 * @create: 2022-04-11 16:14
 **/
public interface CalculationIndexRecordService {

    /**
     * 保存
     *
     * @param calculationIndexRecord : calculationIndexRecord
     * @return void
     * @author Nick
     * @date 2022/04/11
    */
    void save(CalculationIndexRecord calculationIndexRecord);

    /**
     * 获取指标计算记录
     *
     * @param className : className
     * @param merchantNo : merchantNo
     * @param appNo : appNo
     * @param phoneNumber : phoneNumber
     * @param date : date
     * @return java.util.List<com.aurora.calculation.mongo.model.CalculationIndexRecord>
     * @author Nick
     * @date 2022/04/11
    */
    CalculationIndexRecord getCalculationIndexRecord(String className, String merchantNo, String appNo, String phoneNumber, Date date);
}
