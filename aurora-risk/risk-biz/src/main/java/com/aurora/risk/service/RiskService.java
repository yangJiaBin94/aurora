package com.aurora.risk.service;

import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.entity.RiskResult;

/**
 * @author: Nick
 * @create: 2022-03-28 16:37
 **/
public interface RiskService {

    /**
     * 风控主流程方法
     *
     * @param contextInfo : contextInfo
     * @return void
     * @author Nick
     * @date 2022/03/28
    */
    RiskResult risk(RiskContextInfo contextInfo);
}
