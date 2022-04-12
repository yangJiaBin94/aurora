package com.aurora.data.controller;

import com.aurora.common.request.data.*;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.data.*;
import com.aurora.data.service.UserDataProvideService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-14 16:09
 **/
@Slf4j
@RestController
@RequestMapping("data/provide")
public class UserDateProvideController {

    @Resource
    private UserDataProvideService provideService;

    @PostMapping("user/base")
    @ResponseBody
    public BaseResponse<UserBaseInfoProvideResponse> provideUserBaseInfo(@RequestBody UserBaseInfoProvideRequest request) {
        return BaseResponse.successResponse(provideService.provideUserBase(request));
    }

    @PostMapping("user/device")
    @ResponseBody
    public BaseResponse<UserDeviceInfoProvideResponse> provideUserDeviceInfo(@RequestBody UserDeviceInfoProvideRequest request) {
        return BaseResponse.successResponse(provideService.provideUserDevice(request));
    }

    @PostMapping("user/app")
    @ResponseBody
    public BaseResponse<UserAppInfoProvideResponse> provideUserAppInfo(@RequestBody UserAppInfoProvideRequest request) {
        return BaseResponse.successResponse(provideService.provideUserApp(request));
    }

    @PostMapping("user/sms")
    @ResponseBody
    public BaseResponse<UserSmsInfoProvideResponse> provideUserSmsInfo(@RequestBody UserSmsInfoProvideRequest request) {
        return BaseResponse.successResponse(provideService.provideUserSms(request));
    }

    @PostMapping("user/contact")
    @ResponseBody
    public BaseResponse<UserContactInfoProvideResponse> provideUserContactInfo(@RequestBody UserContactInfoProvideRequest request) {
        return BaseResponse.successResponse(provideService.provideUserContact(request));
    }

    @PostMapping("user/photo")
    @ResponseBody
    public BaseResponse<UserPhotoInfoProvideResponse> provideUserPhotoInfo(@RequestBody UserPhotoInfoProvideRequest request) {
        return BaseResponse.successResponse(provideService.provideUserPhoto(request));
    }

}
