package com.aurora.common.request.data;

import lombok.Data;

/**
 * @author: Nick
 * @create: 2022-03-21 11:33
 **/
@Data
public class UserBaseInfoProvideRequest {

    private String userIdentify;

    private String userPhone;

    private String merNo;

    private String appNo;
}
