package com.aurora.calculation.component;

import com.aurora.calculation.annotation.Index;
import com.aurora.calculation.util.ClassUtil;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Nick
 * @create: 2022-03-31 14:27
 **/
@Configuration
public class IndexGroupConfig {

    public static Map<? extends Class<?>, List<Class<?>>> indexGroupStructure() {
        List<Class<?>> classByAnnotation = ClassUtil.getClassByAnnotation(Index.class);
        return classByAnnotation.stream().collect(Collectors.groupingBy(e -> {
            Index annotation = e.getAnnotation(Index.class);
            return annotation.indexGroupActuator();
        }));
    }

    public static Map<? extends Class<?>, List<Class<?>>> indexReceiveStructure() {
        List<Class<?>> classByAnnotation = ClassUtil.getClassByAnnotation(Index.class);
        return classByAnnotation.stream().collect(Collectors.groupingBy(e -> {
            Index annotation = e.getAnnotation(Index.class);
            return annotation.indexReceiver();
        }));
    }
}
