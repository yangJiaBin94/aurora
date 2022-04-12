package com.aurora.risk.service;

import com.aurora.risk.base.model.RiskOrder;
import com.aurora.risk.enums.OrderStatusEnum;

/**
 * @author: Nick
 * @create: 2022-03-25 18:15
 **/
public interface OrderService {

    /**
     * 根据商户订单编号获取风控订单信息
     *
     * @param merOrderNo : merOrderNo
     * @param merchantNo : merchantNo
     * @param appNo : appNo
     * @return com.aurora.risk.base.model.RiskOrder
     * @author Nick
     * @date 2022/03/25
     */
    RiskOrder getOrderByMerOrderNo(String merOrderNo, String merchantNo, String appNo);

    /**
     * 变更订单状态
     *
     * @param orderStatus : orderStatus
     * @param riskOrderNo : riskOrderNo
     * @return void
     * @author Nick
     * @date 2022/03/30
     */
    void changeOrderStatus(OrderStatusEnum orderStatus, String riskOrderNo);
}
