package com.aurora.data.service.impl;

import com.aurora.common.request.data.*;
import com.aurora.common.response.data.*;
import com.aurora.data.service.UserDataProvideService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: Nick
 * @create: 2022-03-21 12:00
 **/
@Slf4j
@Service
public class UserDataProvideServiceImpl implements UserDataProvideService {
    @Override
    public UserBaseInfoProvideResponse provideUserBase(UserBaseInfoProvideRequest request) {
        return null;
    }

    @Override
    public UserDeviceInfoProvideResponse provideUserDevice(UserDeviceInfoProvideRequest userDeviceInfoVo) {
        return null;
    }

    @Override
    public UserContactInfoProvideResponse provideUserContact(UserContactInfoProvideRequest userContactInfoVo) {
        return null;
    }

    @Override
    public UserAppInfoProvideResponse provideUserApp(UserAppInfoProvideRequest userAppInfoVo) {
        return null;
    }

    @Override
    public UserSmsInfoProvideResponse provideUserSms(UserSmsInfoProvideRequest userSmsInfoVo) {
        return null;
    }

    @Override
    public UserPhotoInfoProvideResponse provideUserPhoto(UserPhotoInfoProvideRequest userPhotoInfoVo) {
        return null;
    }
}
