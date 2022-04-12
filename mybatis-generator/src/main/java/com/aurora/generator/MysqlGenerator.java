package com.aurora.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yangjiabin
 */
@Data
public class MysqlGenerator {
    private String serviceProjectPath;
    private String daoProjectPath;
    private String packageName;
    private String moduleName;
    private String tableName;
    private String author;
    private String dbUrl;
    private String dbUser;
    private String dbPwd;

    private AutoGenerator mpg;

    public MysqlGenerator(String daoProjectPath, String serviceProjectPath, String packageName, String moduleName, String tableName, String author, String dbUrl, String dbUser, String dbPwd) {
        this.setDaoProjectPath(daoProjectPath);
        this.setServiceProjectPath(serviceProjectPath);
        this.setPackageName(packageName);
        this.setModuleName(moduleName);
        this.setTableName(tableName);
        this.setAuthor(author);
        this.setDbUrl(dbUrl);
        this.setDbUser(dbUser);
        this.setDbPwd(dbPwd);
    }

    /**
     * @Description: 执行生产
     * @Param: []
     * @return: void
     * @Author: Nick
     * @Date: 2022/03/10
    */
    public void execute() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        this.setMpg(mpg);
        // 全局配置
        mpg.setGlobalConfig(getGlobalConfig());
        // 数据源配置
        mpg.setDataSource(getDataSourceConfig());
        // 包配置
        mpg.setPackageInfo(getPackageConfig());
        // 自定义配置
        mpg.setCfg(getInjectionConfig());
        // 模板配置
        mpg.setTemplate(getTemplateConfig());
        // 策略配置
        mpg.setStrategy(getStrategyConfig());

        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * @Description: 全局配置
     * @Param: []
     * @return: com.aurora.generator.GlobalConfig
     * @Author: Nick
     * @Date: 2022/03/10
    */
    public GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setDaoOutputDir(getDaoProjectPath() + "/src/main/java");
        gc.setServiceOutputDir(getServiceProjectPath() + "/src/main/java");
        gc.setAuthor(getAuthor());
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setEnableCache(false);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setControllerName(null);
        return gc;
    }

    /**
     * @Description: 数据源配置
     * @Param: []
     * @return: com.aurora.generator.DataSourceConfig
     * @Author: Nick
     * @Date: 2022/03/10
    */
    public DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(getDbUrl());
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(getDbUser());
        dsc.setPassword(getDbPwd());
        return dsc;
    }

    /**
     * @Description: 包配置
     * @Param: []
     * @return: com.baomidou.mybatisplus.generator.config.PackageConfig
     * @Author: Nick
     * @Date: 2022/03/10
    */
    public PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setParent(getPackageName());
        pc.setModuleName(getModuleName());
        pc.setEntity("model");
        pc.setMapper("mapper");
        return pc;
    }

    /**
     * @Description: 自定义配置
     * @Param: []
     * @return: com.aurora.generator.InjectionConfig
     * @Author: Nick
     * @Date: 2022/03/10
    */
    public InjectionConfig getInjectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String outPath = getDaoProjectPath() + "/src/main/resources/" + getMpg().getPackageInfo().getParent().replaceAll("\\.", "/") + "/" + getMpg().getPackageInfo().getMapper() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return outPath;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * @Description: 模板配置
     * @Param: []
     * @return: com.baomidou.mybatisplus.generator.config.TemplateConfig
     * @Author: Nick
     * @Date: 2022/03/10
    */
    public TemplateConfig getTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
        //不生成controller
        tc.setController(null);
        //不生成默认的mapper.xml
        tc.setXml(null);
        return tc;
    }

    /**
     * @Description: 策略配置
     * @Param: []
     * @return: com.baomidou.mybatisplus.generator.config.StrategyConfig
     * @Author: Nick
     * @Date: 2022/03/10
    */
    public StrategyConfig getStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
        strategy.setInclude(getTableName());
        strategy.setControllerMappingHyphenStyle(true);
        //字段常量
        strategy.setEntityColumnConstant(true);
        //会在字段属性上加上@TableField
        strategy.setEntityTableFieldAnnotationEnable(true);
        return strategy;
    }


}
