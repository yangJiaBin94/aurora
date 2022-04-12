package com.aurora.risk.feign.service;

import com.aurora.common.request.data.*;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.data.*;
import com.aurora.risk.feign.hystrix.GetUserDataServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: Nick
 * @create: 2022-03-10 13:59
 **/
@FeignClient(name = "gateway-server", fallbackFactory = GetUserDataServiceFallbackFactory.class)
public interface GetUserDataService {

    /**
     * 获取用户基础数据
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
    */
    @PostMapping("data/provide/user/base")
    BaseResponse<UserBaseInfoProvideResponse> getUserBaseInfo(@RequestBody UserBaseInfoProvideRequest request);

    /**
     * 获取用户设备信息
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
     */
    @PostMapping("data/provide/user/device")
    BaseResponse<UserDeviceInfoProvideResponse> getUserDeviceInfo(@RequestBody UserDeviceInfoProvideRequest request);

    /**
     * 获取用户app信息
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
     */
    @PostMapping("data/provide/user/app")
    BaseResponse<UserAppInfoProvideResponse> getUserAppInfo(@RequestBody UserAppInfoProvideRequest request);

    /**
     * 获取用户sms信息
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
     */
    @PostMapping("data/provide/user/sms")
    BaseResponse<UserSmsInfoProvideResponse> getUserSmsInfo(@RequestBody UserSmsInfoProvideRequest request);

    /**
     * 获取用户contact信息
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
     */
    @PostMapping("data/provide/user/contact")
    BaseResponse<UserContactInfoProvideResponse> getUserContactInfo(@RequestBody UserContactInfoProvideRequest request);

    /**
     * 获取用户photo信息
     *
     * @param request : request
     * @return com.aurora.common.response.BaseResponse
     * @author Nick
     * @date 2022/03/10
     */
    @PostMapping("data/provide/user/photo")
    BaseResponse<UserPhotoInfoProvideResponse> getUserPhotoInfo(@RequestBody UserPhotoInfoProvideRequest request);
}
