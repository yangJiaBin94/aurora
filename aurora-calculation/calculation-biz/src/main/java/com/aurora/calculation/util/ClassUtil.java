package com.aurora.calculation.util;

import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author: Nick
 * @create: 2022-03-10 13:59
 **/
public class ClassUtil {

    private static final ApplicationContext applicationContext = BeanUtil.getApplicationContext();

    /**
     * 获取所有标注此注解的类
     *
     * @param annotation annotation
     * @return List<Class < ?>>
     */
    public static List<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotation) {
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(annotation);
        return beansWithAnnotationMap.values().stream().map(Object::getClass).collect(Collectors.toList());
    }


}