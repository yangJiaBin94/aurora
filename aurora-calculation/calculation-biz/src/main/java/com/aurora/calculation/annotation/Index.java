package com.aurora.calculation.annotation;

import com.aurora.calculation.enums.RecalculateTimeType;
import com.aurora.calculation.index.group.DefaultIndexGroupActuator;
import com.aurora.calculation.index.receive.DefaultIndexReceiver;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author: Nick
 * @create: 2022-03-14 14:47
 **/
@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Index {

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * 指标名称
     */
    String name() default "";

    /**
     * 指标重新计算间隔时长（此配置时间内此主体指标不重新进行计算，取上次指标值）
     */
    int recalculateTime() default 0;

    /**
     * 指标重新计算间隔时间类型
     */
    RecalculateTimeType recalculateTimeType() default RecalculateTimeType.ALWAYS;

    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * 分组执行器
     */
    Class<?> indexGroupActuator() default DefaultIndexGroupActuator.class;

    /**
     * 分组执行器
     */
    Class<?> indexReceiver() default DefaultIndexReceiver.class;
}
