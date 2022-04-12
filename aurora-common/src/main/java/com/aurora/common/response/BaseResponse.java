package com.aurora.common.response;

import com.aurora.common.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author: Nick
 * @create: 2022-03-14 15:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public static BaseResponse successResponse() {
        return BaseResponse.builder()
                .code(ResponseEnum.SUCCESS.getCode())
                .msg(ResponseEnum.SUCCESS.getMessage())
                .build();
    }

    public static <T> BaseResponse<T> successResponse(T data) {
        return new BaseResponse<>(
                ResponseEnum.SUCCESS.getCode(),
                ResponseEnum.SUCCESS.getMessage(),
                data
        );
    }

    public static <T> BaseResponse<T> failResponse(String code, T data) {
        return new BaseResponse<>(
                code,
                ResponseEnum.FAIL.getMessage(),
                data
        );
    }

    public static BaseResponse failResponse() {
        return BaseResponse.builder()
                .code(ResponseEnum.FAIL.getCode())
                .msg(ResponseEnum.FAIL.getMessage())
                .build();
    }

    public static BaseResponse failResponse(String code, String message) {
        return BaseResponse.builder()
                .code(code)
                .msg(message)
                .build();
    }

    public static BaseResponse failResponse(String message) {
        return BaseResponse.builder()
                .code(ResponseEnum.FAIL.getCode())
                .msg(message)
                .build();
    }


    public static BaseResponse getCommonResponse(ResponseEnum responseEnum, Object data) {
        return BaseResponse.builder()
                .code(responseEnum.getCode())
                .msg(responseEnum.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse getCommonResponse(ResponseEnum responseEnum) {
        return BaseResponse.builder()
                .code(responseEnum.getCode())
                .msg(responseEnum.getMessage())
                .build();
    }
}

