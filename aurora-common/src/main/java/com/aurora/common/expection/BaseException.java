package com.aurora.common.expection;


import com.aurora.common.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Nick
 * @create: 2022-03-14 15:26
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private ResponseEnum statusEnum;

    public BaseException(String message) {
        super(message);
        this.statusEnum = ResponseEnum.FAIL;
    }

    public BaseException(ResponseEnum statusEnum) {
        super(statusEnum.getMessage());
        this.statusEnum = statusEnum;
    }

    public BaseException(ResponseEnum statusEnum, String message) {
        super(message);
        this.statusEnum = statusEnum;
    }
}
