package com.aurora.calculation.index.receive;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: Nick
 * @create: 2022-04-07 15:04
 **/
public interface IndexReceiver {

    /**
     * 接收三方回调方法
     *
     * @param request : request
     * @return void
     * @author Nick
     * @date 2022/04/07
    */
    void receive(JSONObject request);
}
