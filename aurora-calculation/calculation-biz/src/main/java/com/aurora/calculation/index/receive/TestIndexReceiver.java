package com.aurora.calculation.index.receive;

import com.alibaba.fastjson.JSONObject;
import com.aurora.calculation.component.IndexGroupConfig;
import com.aurora.calculation.context.CalculationContext;
import com.aurora.calculation.context.CalculationContextInfo;
import com.aurora.calculation.context.IndexInfo;
import com.aurora.calculation.factory.IndexActuatorFactory;
import com.aurora.calculation.index.IndexAsyncActuator;
import com.aurora.calculation.index.group.DefaultIndexGroupActuator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 异步三方回调接收api
 *
 * @author: Nick
 * @create: 2022-04-07 15:15
 **/
@RestController
@RequestMapping("calculation/api")
public class TestIndexReceiver extends AbstractIndexReceiver {

    @Resource
    private CalculationContext calculationContext;


    @PostMapping("/receive/test")
    @ResponseBody
    @Override
    public void receive(@RequestBody JSONObject request) {

        //TODO 保存返回信息到调用记录
        Map<? extends Class<?>, List<Class<?>>> receiveStructure = IndexGroupConfig.indexReceiveStructure();
        CalculationContextInfo contextInfo = calculationContext.getCalculationContextInfo(request.getString("riskOrderNo"));
        //获取依赖此三方数据的所有指标集合类
        List<String> classList = receiveStructure.get(this.getClass()).stream().map(Class::getName).collect(Collectors.toList());
        //获取依赖此三方数据计算的所有指标信息集合对象并将除默认分组外的指标分组去重（保证后续执行计算操作其分组执行器只执行一次）
        List<IndexInfo> indexInfoList = contextInfo.getIndexInfoList().stream().filter(e -> classList.contains(e.getClassName()))
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(
                                Comparator.comparing(e -> {
                                    //若默认分组指标则保留，其他分组则去重
                                    return e.getGroupClassName().equals(DefaultIndexGroupActuator.class.getName()) ? e.getClassName() : e.getGroupClassName();
                                }))), ArrayList::new));
        //计算指标
        indexInfoList.forEach(indexInfo -> {
            String className;
            //若有分组则获取分组指标执行器对象
            if (DefaultIndexGroupActuator.class.getName().equals(indexInfo.getGroupClassName())) {
                className = indexInfo.getClassName();
            } else {
                //没有分组则获取当前指标执行器对象
                className = indexInfo.getGroupClassName();
            }
            IndexAsyncActuator asyncActuator = IndexActuatorFactory.getAsyncActuator(className);
            if (asyncActuator != null) {
                //执行计算
                asyncActuator.calculation(contextInfo, request);
            }
        });
        //执行
        calculationContext.execute(contextInfo);

    }
}
