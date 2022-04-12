package com.aurora.generator;

import com.baomidou.mybatisplus.generator.config.rules.IColumnType;


/**
 * @author yangjiabin
 */
public interface ITypeConvert {

    /**
     * 执行类型转换
     *
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return ignore
     */
    IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType);

}
