package com.aurora.data.controller;

import com.aurora.common.request.data.UserBaseInfoInfoReceiveRequest;
import com.aurora.common.response.BaseResponse;
import com.aurora.data.service.UserDataReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: Nick
 * @create: 2022-03-14 16:09
 **/
@Slf4j
@RestController
@RequestMapping("data/receive")
public class UserDateReceiveController {

    @Resource
    private UserDataReceiveService userDataReceiveService;

    @PostMapping("user/base")
    @ResponseBody
    public BaseResponse receiveUserBaseInfo(@RequestBody UserBaseInfoInfoReceiveRequest request){
        userDataReceiveService.saveUserBase(request);
        return BaseResponse.successResponse();
    }

}
