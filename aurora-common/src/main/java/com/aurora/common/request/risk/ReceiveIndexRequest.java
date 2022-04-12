package com.aurora.common.request.risk;

import lombok.Data;

import java.util.Map;

/**
 * @author: Nick
 * @create: 2022-04-02 13:50
 **/
@Data
public class ReceiveIndexRequest {

    private String strategyNo;

    private String riskOrderNo;

    private Map<String,String> indexMap;
}
