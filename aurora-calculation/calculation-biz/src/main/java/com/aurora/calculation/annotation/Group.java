package com.aurora.calculation.annotation;

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
public @interface Group {

    @AliasFor(annotation = Component.class)
    String value() default "";

    long keepAlive() default 60;
}
