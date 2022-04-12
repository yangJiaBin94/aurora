package com.aurora.calculation.context;

import com.alibaba.fastjson.JSONObject;
import com.aurora.calculation.annotation.Index;
import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.enums.IndexExecuteStatusEnum;
import com.aurora.calculation.factory.IndexActuatorFactory;
import com.aurora.calculation.feign.service.IndexCallBackService;
import com.aurora.calculation.index.IndexActuator;
import com.aurora.calculation.index.group.DefaultIndexGroupActuator;
import com.aurora.calculation.mongo.model.CalculationIndexRecord;
import com.aurora.calculation.mongo.model.CalculationRecord;
import com.aurora.calculation.service.CalculationIndexRecordService;
import com.aurora.calculation.service.CalculationRecordService;
import com.aurora.calculation.util.BeanUtil;
import com.aurora.calculation.util.RedisKeyUtil;
import com.aurora.common.request.calculation.GetIndexRequest;
import com.aurora.common.request.risk.ReceiveIndexRequest;
import com.aurora.common.response.calculation.GetIndexResponse;
import com.aurora.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author: Nick
 * @create: 2022-04-01 13:54
 **/
@Slf4j
@Service
public class CalculationContextImpl implements CalculationContext {

    private static final String LOG_PREFIX = LogPrefixConstant.CALCULATION_CONTEXT + "CalculationContextImpl - ";

    @Resource
    private ThreadPoolTaskExecutor calculationThreadPool;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private IndexCallBackService indexCallBackService;

    @Resource
    private CalculationRecordService calculationRecordService;

    @Override
    public CalculationContextInfo buildContextInfo(GetIndexRequest request) {
        CalculationContextInfo contextInfo = CommonUtil.transferDate(request, CalculationContextInfo.class);
        List<String> indexClassNameList = request.getIndexClassNameList();
        AtomicReference<Boolean> isAsync = new AtomicReference<>(false);
        List<IndexInfo> indexInfoList = indexClassNameList.stream().map(e -> {
            IndexInfo indexInfo = new IndexInfo();
            indexInfo.setClassName(e);
            try {
                Index annotation = Class.forName(e).getAnnotation(Index.class);
                indexInfo.setIsAsync(annotation.async());
                if (annotation.async()) {
                    isAsync.set(true);
                }
                indexInfo.setGroupClassName(annotation.indexGroupActuator().getName());
                indexInfo.setReceiveClassName(annotation.indexReceiver().getName());
                indexInfo.setRecalculateTimeType(annotation.recalculateTimeType());
                indexInfo.setRecalculateTime(annotation.recalculateTime());
                return indexInfo;
            } catch (ClassNotFoundException exception) {
                log.error(LOG_PREFIX + "指标类：{} 不存在", e, exception);
                //指标类不存在则返回值为null 执行状态置为完成
                indexInfo.setStatusEnum(IndexExecuteStatusEnum.DONE);
                return indexInfo;
            }
        }).collect(Collectors.toList());
        contextInfo.setIndexInfoList(indexInfoList);
        contextInfo.setIsAsync(isAsync.get());
        return contextInfo;
    }

    @Override
    public void execute(CalculationContextInfo contextInfo) {
        //获取要计算的指标集合
        List<IndexInfo> indexInfoList = contextInfo.getIndexInfoList();
        //过滤掉已经执行完成的指标 并按其分组执行器className进行分组
        Map<String, List<IndexInfo>> indexGroupMap = indexInfoList.stream()
                .filter(e -> !IndexExecuteStatusEnum.DONE.equals(e.getStatusEnum()))
                .collect(Collectors.groupingBy(IndexInfo::getGroupClassName));
        //异步执行
        if (contextInfo.getIsAsync()) {
            calculationThreadPool.execute(() -> {
                execute(contextInfo, indexGroupMap);
            });
        } else {
            //同步执行
            execute(contextInfo, indexGroupMap);
        }
    }

    @Override
    public void saveOrUpdateContext(CalculationContextInfo contextInfo) {
        String key = RedisKeyUtil.getContextCacheRedisKey(contextInfo.getRiskOrderNo());
        RBucket<CalculationContextInfo> bucket = redissonClient.getBucket(key);
        bucket.set(contextInfo);
    }

    @Override
    public CalculationContextInfo getCalculationContextInfo(String riskOrderNo) {
        String key = RedisKeyUtil.getContextCacheRedisKey(riskOrderNo);
        RBucket<CalculationContextInfo> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 执行
     *
     * @param contextInfo   : contextInfo
     * @param indexGroupMap : indexGroupMap
     * @return void
     * @author Nick
     * @date 2022/04/07
     */
    private void execute(CalculationContextInfo contextInfo, Map<String, List<IndexInfo>> indexGroupMap) {
        CountDownLatch groupLatch = new CountDownLatch(indexGroupMap.size());
        //按组执行指标执行器
        indexGroupMap.forEach((groupActuatorName, indexList) -> calculationThreadPool.execute(() -> {
            //执行分组执行器
            if (!groupActuatorName.equals(DefaultIndexGroupActuator.class.getName())) {
                IndexActuator groupActuator = IndexActuatorFactory.getActuator(groupActuatorName);
                if (groupActuator != null) {
                    groupActuator.execute(contextInfo);
                }
            }
            //执行当前组内指标执行器
            CountDownLatch indexLatch = new CountDownLatch(indexList.size());
            indexList.forEach(e -> calculationThreadPool.execute(() -> {
                IndexActuator actuator = IndexActuatorFactory.getActuator(e.getClassName());
                if (actuator != null) {
                    actuator.execute(contextInfo);
                }
                indexLatch.countDown();
            }));
            try {
                indexLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            groupLatch.countDown();
        }));
        try {
            groupLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (contextInfo.getIsAsync()) {
            //若所有指标执行完成
            if (checkIndexIsAllDone(contextInfo.getIndexInfoList())) {
                //保存执行上下文记录
                saveRecord(contextInfo);
                //清除上下文缓存
                String key = RedisKeyUtil.getContextCacheRedisKey(contextInfo.getRiskOrderNo());
                RBucket<CalculationContextInfo> bucket = redissonClient.getBucket(key);
                bucket.deleteAsync();
                //回调客户端结果
                GetIndexResponse response = buildResponse(contextInfo);
                indexCallBackService.receiveIndex(CommonUtil.transferDate(response, ReceiveIndexRequest.class));
            }
        } else {
            //保存执行上下文记录
            saveRecord(contextInfo);
        }
    }

    /**
     * 保存执行记录上下文
     *
     * @param contextInfo : contextInfo
     * @return void
     * @author Nick
     * @date 2022/04/12
     */
    private void saveRecord(CalculationContextInfo contextInfo) {
        CalculationRecord calculationRecord = CommonUtil.transferDate(contextInfo, CalculationRecord.class);
        calculationRecord.setCreateTime(new Date());
        calculationRecord.setContext(JSONObject.toJSONString(contextInfo));
        calculationRecordService.save(calculationRecord);
    }

    /**
     * 是否所有指标执行完毕
     *
     * @param indexInfoList : indexInfoList
     * @return boolean
     * @author Nick
     * @date 2022/04/11
     */
    private boolean checkIndexIsAllDone(List<IndexInfo> indexInfoList) {
        return indexInfoList.stream().allMatch(e -> e.getStatusEnum().equals(IndexExecuteStatusEnum.DONE));
    }

    @Override
    public GetIndexResponse buildResponse(CalculationContextInfo contextInfo) {
        GetIndexResponse response = CommonUtil.transferDate(contextInfo, GetIndexResponse.class);
        List<IndexInfo> indexInfoList = contextInfo.getIndexInfoList();
        Map<String, String> indexMap = indexInfoList.stream().collect(Collectors.toMap(IndexInfo::getClassName, IndexInfo::getValue));
        response.setIndexMap(indexMap);
        return response;
    }

    @Override
    public Date getMaxRecalculateDate(IndexInfo indexInfo) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (indexInfo.getRecalculateTimeType()) {
            case NEVER:
                return new Date(0);
            case SECONDS:
                calendar.add(Calendar.SECOND, -indexInfo.getRecalculateTime());
                return calendar.getTime();
            case MINUTES:
                calendar.add(Calendar.MINUTE, -indexInfo.getRecalculateTime());
                return calendar.getTime();
            case HOURS:
                calendar.add(Calendar.HOUR_OF_DAY, -indexInfo.getRecalculateTime());
                return calendar.getTime();
            case DAYS:
                calendar.add(Calendar.DATE, -indexInfo.getRecalculateTime());
                return calendar.getTime();
            default:
                return null;
        }
    }

    /**
     * 保存指标记录
     *
     * @param contextInfo   : contextInfo
     * @param indexInfo     : indexInfo
     * @param isCalculation : isCalculation
     * @return void
     * @author Nick
     * @date 2022/04/12
     */
    @Override
    public void saveIndexRecord(CalculationContextInfo contextInfo, IndexInfo indexInfo, Boolean isCalculation) {
        //保存指标计算记录到数据库
        CalculationIndexRecordService calculationIndexRecordService = BeanUtil.getBean(CalculationIndexRecordService.class);
        CalculationIndexRecord record = CommonUtil.transferDate(contextInfo, CalculationIndexRecord.class);
        record.setClassName(indexInfo.getClassName());
        record.setCreateTime(new Date());
        record.setIsCalculation(isCalculation);
        record.setGroupClassName(indexInfo.getGroupClassName());
        record.setValue(indexInfo.getValue());
        calculationIndexRecordService.save(record);
    }
}
