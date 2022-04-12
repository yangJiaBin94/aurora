package com.aurora.generator;


/**
 * @author yangjiabin
 */
public class MybatisGenerator {

    public static void tableGenerator(String[] tables, String moduleName) {
        //数据库配置
        String dbUrl = "jdbc:mysql://localhost:3306/aurora_risk?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPwd = "123456";
        //项目包配置
        String author = "Nick";
        String daoProjectPath = "aurora-risk/risk-data";
        String serviceProjectPath = "aurora-risk/risk-biz";
        String packageName = "com.aurora.risk";

        for (String tableName : tables) {
            tableName = tableName.trim();
            MysqlGenerator mg = new MysqlGenerator(daoProjectPath, serviceProjectPath, packageName, moduleName, tableName, author, dbUrl, dbUser, dbPwd);
            mg.execute();
        }
    }

    public static void baseGenerator(String... tabs) {
        String[] tables = new String[]{};
        if (tabs != null && tabs.length > 0) {
            tables = tabs;
        }
        tableGenerator(tables, "base");
    }

    public static void loan() {
        String[] tables = new String[]{
                "risk_rule",
                "risk_scene",
                "risk_strategy",
                "risk_scene_strategy",
                "risk_strategy_rule",
                "risk_abtest_strategy",
                "risk_app_scene",
                "risk_abtest",
                "risk_app",
                "risk_merchant",
                "risk_order",
                "risk_strategy_record",
                "risk_rule_group",
                "risk_order_record",

        };
        baseGenerator(tables);
    }

    public static void main(String[] args) {
        loan();
    }
}
