package com.aurora.risk.feign.hystrix;

import com.aurora.common.request.data.*;
import com.aurora.common.request.loan.LoanApiCallBackRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.common.response.data.*;
import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.feign.service.GetUserDataService;
import com.aurora.risk.feign.service.LoanApiCallBackService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: Nick
 * @create: 2022-03-10 14:11
 **/
@Slf4j
@Component
public class GetUserDataServiceFallbackFactory implements FallbackFactory<GetUserDataService> {

    private static final String LOG_PREFIX = LogPrefixConstant.RISK_FEIGN_HYSTRIX+"[GetUserDataServiceFallbackFactory] - ";
    @Override
    public GetUserDataService create(Throwable cause) {
        log.error(LOG_PREFIX+"获取用户基础数据异常，服务熔断：{}",cause.getMessage());
        return new GetUserDataService() {

            @Override
            public BaseResponse<UserBaseInfoProvideResponse> getUserBaseInfo(UserBaseInfoProvideRequest request) {
                return BaseResponse.successResponse(null);
            }

            @Override
            public BaseResponse<UserDeviceInfoProvideResponse> getUserDeviceInfo(UserDeviceInfoProvideRequest request) {
                return BaseResponse.successResponse(null);
            }

            @Override
            public BaseResponse<UserAppInfoProvideResponse> getUserAppInfo(UserAppInfoProvideRequest request) {
                return BaseResponse.successResponse(null);
            }

            @Override
            public BaseResponse<UserSmsInfoProvideResponse> getUserSmsInfo(UserSmsInfoProvideRequest request) {
                return BaseResponse.successResponse(null);
            }

            @Override
            public BaseResponse<UserContactInfoProvideResponse> getUserContactInfo(UserContactInfoProvideRequest request) {
                return BaseResponse.successResponse(null);
            }

            @Override
            public BaseResponse<UserPhotoInfoProvideResponse> getUserPhotoInfo(UserPhotoInfoProvideRequest request) {
                return BaseResponse.successResponse(null);
            }
        };
    }
}
