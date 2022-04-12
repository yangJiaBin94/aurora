package com.aurora.calculation.service.impl;

import com.aurora.calculation.mongo.model.CalculationRecord;
import com.aurora.calculation.service.CalculationRecordService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-04-11 16:13
 **/
@Service
public class CalculationRecordServiceImpl implements CalculationRecordService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(CalculationRecord record) {
        mongoTemplate.save(record);
    }
}
