package com.aurora.risk.service.impl;

import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import com.aurora.risk.base.model.RiskOrder;
import com.aurora.risk.base.model.RiskOrderRecord;
import com.aurora.risk.base.service.IRiskOrderRecordService;
import com.aurora.risk.base.service.IRiskOrderService;
import com.aurora.risk.enums.OrderStatusEnum;
import com.aurora.risk.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZoneId;

/**
 * @author: Nick
 * @create: 2022-03-25 18:17
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private IRiskOrderService orderService;

    @Resource
    private IRiskOrderRecordService riskOrderRecordService;

    @Override
    public RiskOrder getOrderByMerOrderNo(String merOrderNo, String merchantNo, String appNo) {
        return orderService.getOne(new QueryWrapper<RiskOrder>()
                .eq(RiskOrder.MER_ORDER_NO, merOrderNo)
                .eq(RiskOrder.MERCHANT_NO, merchantNo)
                .eq(RiskOrder.APP_NO, appNo)
        );
    }

    @Override
    public void changeOrderStatus(OrderStatusEnum orderStatus, String riskOrderNo) {

        RiskOrder order = orderService.getOne(new QueryWrapper<RiskOrder>().eq(RiskOrder.RISK_ORDER_NO, riskOrderNo));
        int lastStatus = order.getOrderStatus();
        long orderCreateTime = order.getCreateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        //校验订单状态
        switch (orderStatus) {
            case ON_GOING:
                if (!OrderStatusEnum.INIT.status.equals(order.getOrderStatus())) {
                    throw new BaseException(ResponseEnum.RISK_ORDER_1005);
                }
                break;
            case EXE_SUCCESS:
            case EXE_FAIL:
            case REVIEW:
                if (!OrderStatusEnum.ON_GOING.status.equals(order.getOrderStatus())) {
                    throw new BaseException(ResponseEnum.RISK_ORDER_1005);
                }
                break;
            case REVIEW_OVER:
                if (!OrderStatusEnum.REVIEW.status.equals(order.getOrderStatus())) {
                    throw new BaseException(ResponseEnum.RISK_ORDER_1005);
                }
                break;
            case NOTICE_SUCCESS:
            case NOTICE_FAIL:
                if (!(OrderStatusEnum.REVIEW_OVER.status.equals(order.getOrderStatus()) || OrderStatusEnum.EXE_SUCCESS.status.equals(order.getOrderStatus()))) {
                    throw new BaseException(ResponseEnum.RISK_ORDER_1005);
                }
                break;
            default:
        }
        //计算订单耗时
        order.setRunTime(System.currentTimeMillis() - orderCreateTime);
        order.setOrderStatus(orderStatus.status);
        orderService.updateById(order);

        //保存订单状态变更记录
        RiskOrderRecord riskOrderRecord = new RiskOrderRecord();
        riskOrderRecord.setLastStatus(lastStatus);
        riskOrderRecord.setNowStatus(orderStatus.status);
        riskOrderRecord.setRiskOrderNo(riskOrderNo);

        //计算订单上一个状态持续时间
        long lastStatusTime = orderCreateTime;
        if (order.getUpdateTime() != null) {
            lastStatusTime = order.getUpdateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        riskOrderRecord.setRunTime(System.currentTimeMillis() - lastStatusTime);
        riskOrderRecordService.save(riskOrderRecord);

    }
}
