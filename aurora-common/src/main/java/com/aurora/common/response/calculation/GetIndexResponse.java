package com.aurora.common.response.calculation;

import lombok.Data;

import java.util.Map;

/**
 * @author: Nick
 * @create: 2022-04-02 13:50
 **/
@Data
public class GetIndexResponse {

    private String strategyNo;

    private String riskOrderNo;

    private Map<String,String> indexMap;
}
