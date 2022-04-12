package com.aurora.risk.base.service.impl;

import com.aurora.risk.base.model.RiskOrderRecord;
import com.aurora.risk.base.mapper.RiskOrderRecordMapper;
import com.aurora.risk.base.service.IRiskOrderRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 风控订单状态变更记录 服务实现类
 * </p>
 *
 * @author Nick
 * @since 2022-04-06
 */
@Service
public class RiskOrderRecordServiceImpl extends ServiceImpl<RiskOrderRecordMapper, RiskOrderRecord> implements IRiskOrderRecordService {

}
