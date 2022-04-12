package com.aurora.data.service;

import com.aurora.common.request.data.*;

/**
 * @author: Nick
 * @create: 2022-03-14 16:17
 **/
public interface UserDataReceiveService {

    /**
     * 保存用户基础信息
     *
     * @param userBaseInfoVo : userBaseInfoVo
     * @author Nick
     * @date 2022/03/14
     */
    void saveUserBase(UserBaseInfoInfoReceiveRequest userBaseInfoVo);


    /**
     * 保存用户设备信息
     *
     * @param userDeviceInfoVo : userDeviceInfoVo
     * @author Nick
     * @date 2022/03/14
     */
    void saveUserDevice(UserDeviceInfoReceiveRequest userDeviceInfoVo);

    /**
     * 保存用户通讯录信息
     *
     * @param userContactInfoVo : userContactInfoVo
     * @author Nick
     * @date 2022/03/14
     */
    void saveUserContact(UserContactInfoReceiveRequest userContactInfoVo);

    /**
     * 保存用户APP信息
     *
     * @param userAppInfoVo : userAppInfoVo
     * @author Nick
     * @date 2022/03/14
     */
    void saveUserApp(UserAppInfoInfoReceiveRequest userAppInfoVo);

    /**
     * 保存用户SMS信息
     *
     * @param userSmsInfoVo : userSmsInfoVo
     * @author Nick
     * @date 2022/03/14
     */
    void saveUserSms(UserSmsInfoReceiveRequest userSmsInfoVo);

    /**
     * 保存用户Photo信息
     *
     * @param userPhotoInfoVo : userPhotoInfoVo
     * @author Nick
     * @date 2022/03/14
     */
    void saveUserPhoto(UserPhotoInfoReceiveRequest userPhotoInfoVo);
}
