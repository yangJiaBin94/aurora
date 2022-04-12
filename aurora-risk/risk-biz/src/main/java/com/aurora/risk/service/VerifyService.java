package com.aurora.risk.service;

/**
 * @author: Nick
 * @create: 2022-03-29 10:39
 **/
public interface VerifyService {

    /**
     * 校验商户
     *
     * @param merchantNo : merchantNo
     * @return boolean
     * @author Nick
     * @date 2022/03/29
    */
    boolean verifyMerchant(String merchantNo);

    /**
     * 校验应用
     *
     * @param appNo : appNo
     * @return boolean
     * @author Nick
     * @date 2022/03/29
    */
    boolean verifyApp(String appNo);

    /**
     * 检查商户应用场景配置是否正常
     *
     * @param appNo : appNo
     * @param merchantNo : merchantNo
     * @param sceneNo : sceneNo
     * @return boolean
     * @author Nick
     * @date 2022/03/29
     */
    boolean verifySceneConfig(String appNo, String merchantNo,String sceneNo);
}
