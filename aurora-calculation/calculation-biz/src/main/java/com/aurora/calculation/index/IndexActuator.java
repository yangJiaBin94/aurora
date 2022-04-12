package com.aurora.calculation.index;

import com.aurora.calculation.context.CalculationContextInfo;
import org.redisson.api.RMap;

import java.util.List;
import java.util.Map;

/**
 * 指标执行器接口
 *
 * @author: Nick
 * @create: 2022-03-14 14:47
 **/
public interface IndexActuator {

    /**
     * 获取指标
     *
     * @param getIndexVo : getIndexVo
     * @return java.lang.String
     * @author Nick
     * @date 2022/03/14
     */
    void calculation(CalculationContextInfo getIndexVo);

    /**
     * 执行
     *
     * @param getIndexVo : getIndexVo
     * @return void
     * @author Nick
     * @date 2022/04/11
     */
    void execute(CalculationContextInfo getIndexVo);

    /**
     * 校验
     *
     * @param indexGroupStructure : indexGroupStructure
     * @param map                 : map
     * @return java.lang.String
     * @author Nick
     * @date 2022/04/06
     */
    String verify(Map<? extends Class<?>, List<Class<?>>> indexGroupStructure, RMap<String, String> map);

    /**
     * 获取缓存map
     *
     * @param contextInfo : contextInfo
     * @return org.redisson.api.RMap<java.lang.String, java.lang.String>
     * @author Nick
     * @date 2022/04/11
     */
    RMap<String, String> getCacheMap(CalculationContextInfo contextInfo);


}
