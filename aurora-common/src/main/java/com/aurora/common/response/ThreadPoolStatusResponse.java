package com.aurora.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: Nick
 * @create: 2022-03-25 14:38
 **/
@Data
@AllArgsConstructor
public class ThreadPoolStatusResponse {

    private int currentPoolSize;
    private int corePoolSize;
    private int maximumPoolSize;
    private int activeTaskCount;
    private long completedTaskCount;
    private long totalTaskCount;
    private boolean isTerminated;
    private long rejectedCount;
}
