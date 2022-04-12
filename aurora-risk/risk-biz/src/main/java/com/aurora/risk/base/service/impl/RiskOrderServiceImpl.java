package com.aurora.risk.base.service.impl;

import com.aurora.risk.base.model.RiskOrder;
import com.aurora.risk.base.mapper.RiskOrderMapper;
import com.aurora.risk.base.service.IRiskOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 风控订单 服务实现类
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Service
public class RiskOrderServiceImpl extends ServiceImpl<RiskOrderMapper, RiskOrder> implements IRiskOrderService {

}
