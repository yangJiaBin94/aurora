package com.aurora.calculation.factory;

import com.aurora.calculation.constant.LogPrefixConstant;
import com.aurora.calculation.index.IndexAsyncActuator;
import com.aurora.calculation.index.IndexActuator;
import com.aurora.calculation.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Nick
 * @create: 2022-03-14 15:41
 **/
@Slf4j
public class IndexActuatorFactory {

    private static final String LOGGER_PREFIX = LogPrefixConstant.CALCULATION_FACTORY + "[IndexHandlerFactory] - ";

    public static IndexActuator getActuator(String className) {
        try {
            return (IndexActuator) BeanUtil.getBean(Class.forName(className));
        } catch (Exception e) {
            log.error(LOGGER_PREFIX + "获取IndexHandler异常 className: {}", className, e);
            return null;
        }

    }

    public static IndexActuator getActuator(Class<?> cls) {
        try {
            return (IndexActuator) BeanUtil.getBean(cls);
        } catch (Exception e) {
            log.error(LOGGER_PREFIX + "获取IndexHandler异常 className: {}", cls.getName(), e);
            return null;
        }

    }

    public static IndexAsyncActuator getAsyncActuator(String className) {
        try {
            return (IndexAsyncActuator) BeanUtil.getBean(Class.forName(className));
        } catch (Exception e) {
            log.error(LOGGER_PREFIX + "获取IndexHandler异常 className: {}", className, e);
            return null;
        }

    }

    public static IndexAsyncActuator getAsyncActuator(Class<?> cls) {
        try {
            return (IndexAsyncActuator) BeanUtil.getBean(cls);
        } catch (Exception e) {
            log.error(LOGGER_PREFIX + "获取IndexHandler异常 className: {}", cls.getName(), e);
            return null;
        }

    }
}
