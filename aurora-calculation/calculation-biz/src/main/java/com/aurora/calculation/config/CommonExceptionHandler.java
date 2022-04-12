package com.aurora.calculation.config;

import com.aurora.common.enums.ResponseEnum;
import com.aurora.common.expection.BaseException;
import com.aurora.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *
 * @author: Nick
 * @create: 2022-03-09 18:02
 **/
@ResponseBody
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public BaseResponse apiBaseException(BaseException e, HttpServletResponse response, HandlerMethod method) {
        return this.process(e, response, method);
    }

    @ExceptionHandler({Exception.class})
    public BaseResponse apiException(Exception e, HttpServletResponse response, HandlerMethod method) {
        return this.process(e, response, method);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResponse apiNotValidException(Exception e, HttpServletResponse response, HandlerMethod method) {
        return this.process(e, response, method);
    }

    private BaseResponse process(Throwable e, HttpServletResponse response, HandlerMethod method) {
        if (e instanceof BaseException) {
            ResponseEnum statusEnum = ((BaseException) e).getStatusEnum();
            if (!ResponseEnum.FAIL.getCode().equals(statusEnum.getCode())) {
                return BaseResponse.getCommonResponse(statusEnum);
            } else {
                return BaseResponse.failResponse(e.getMessage());
            }
        } else if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            String msg = "校验失败:";
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage());
            }
            msg = msg + sb.toString();
            return BaseResponse.failResponse(msg);
        } else if (e instanceof ConstraintViolationException) {
            return BaseResponse.failResponse(e.getMessage());
        } else {
            log.error("系统异常:", e);
            return BaseResponse.failResponse(e.getMessage());
        }
    }
}
