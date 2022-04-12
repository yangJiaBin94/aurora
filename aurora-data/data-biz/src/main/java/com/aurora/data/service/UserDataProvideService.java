package com.aurora.data.service;

import com.aurora.common.request.data.*;
import com.aurora.common.response.data.*;

/**
 * @author: Nick
 * @create: 2022-03-14 16:17
 **/
public interface UserDataProvideService {

    /**
     * 提供用户基础信息
     *
     * @param request : request
     * @author Nick
     * @return  UserBaseInfoProvideResponse
     * @date 2022/03/14
    */
    UserBaseInfoProvideResponse provideUserBase(UserBaseInfoProvideRequest request);


    /**
     * 提供用户设备信息
     *
     * @param userDeviceInfoVo : userDeviceInfoVo
     * @author Nick
     * @return UserDeviceInfoProvideResponse
     * @date 2022/03/14
    */
    UserDeviceInfoProvideResponse provideUserDevice(UserDeviceInfoProvideRequest userDeviceInfoVo);

    /**
     * 提供用户通讯录信息
     *
     * @param userContactInfoVo : userContactInfoVo
     * @author Nick
     * @return  UserContactInfoProvideResponse
     * @date 2022/03/14
    */
    UserContactInfoProvideResponse provideUserContact(UserContactInfoProvideRequest userContactInfoVo);

    /**
     * 提供用户APP信息
     *
     * @param userAppInfoVo : userAppInfoVo
     * @author Nick
     * @return  UserAppInfoInfoProvideResponse
     * @date 2022/03/14
    */
    UserAppInfoProvideResponse provideUserApp(UserAppInfoProvideRequest userAppInfoVo);

    /**
     * 提供用户SMS信息
     *
     * @param userSmsInfoVo : userSmsInfoVo
     * @author Nick
     * @return  UserSmsInfoProvideResponse
     * @date 2022/03/14
    */
    UserSmsInfoProvideResponse provideUserSms(UserSmsInfoProvideRequest userSmsInfoVo);

    /**
     * 提供用户Photo信息
     *
     * @param userPhotoInfoVo : userPhotoInfoVo
     * @author Nick
     * @return  UserPhotoInfoProvideResponse
     * @date 2022/03/14
    */
    UserPhotoInfoProvideResponse provideUserPhoto(UserPhotoInfoProvideRequest userPhotoInfoVo);
}
