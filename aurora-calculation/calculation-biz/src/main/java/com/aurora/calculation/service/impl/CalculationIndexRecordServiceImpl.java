package com.aurora.calculation.service.impl;

import com.aurora.calculation.mongo.model.CalculationIndexRecord;
import com.aurora.calculation.service.CalculationIndexRecordService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: Nick
 * @create: 2022-04-11 16:13
 **/
@Service
public class CalculationIndexRecordServiceImpl implements CalculationIndexRecordService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(CalculationIndexRecord calculationIndexRecord) {
        mongoTemplate.save(calculationIndexRecord);

    }

    @Override
    public CalculationIndexRecord getCalculationIndexRecord(String className, String merchantNo, String appNo, String phoneNumber, Date date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("className").is(className));
        query.addCriteria(Criteria.where("merchantNo").is(merchantNo));
        query.addCriteria(Criteria.where("appNo").is(appNo));
        query.addCriteria(Criteria.where("phoneNumber").is(phoneNumber));
        query.addCriteria(Criteria.where("isCalculation").is(true));
        query.addCriteria(Criteria.where("createTime").gte(date));
        query.with(Sort.by(Sort.Order.desc("createTime")));
        List<CalculationIndexRecord> recordList = mongoTemplate.find(query, CalculationIndexRecord.class);
        //取最新记录
        if (!CollectionUtils.isEmpty(recordList)) {
            return recordList.get(0);

        }
        return null;
    }


}
