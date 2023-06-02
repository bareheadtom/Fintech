package com.faas.database;

import cn.hutool.db.Db;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DbExcutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbExcutor.class);
    public static final Db DB;
    static {
        DataSource ds = null;
        try {
//            Map<String, String> dataSourceMap = new HashMap<>();
//            dataSourceMap.put("driver-class-name", SystemUtil.get("faas.mysql.driver-class-name"));
//            dataSourceMap.put("url", SystemUtil.get("faas.mysql.url"));
//            dataSourceMap.put("username", SystemUtil.get("faas.mysql.username"));
//            dataSourceMap.put("password", SystemUtil.get("faas.mysql.password"));
//            dataSourceMap.put("minIdle", SystemUtil.get("faas.mysql.minIdle", "1"));
//            dataSourceMap.put("initialSize", SystemUtil.get("faas.mysql.initialSize", "1"));
//            ds = DruidDataSourceFactory.createDataSource(dataSourceMap);
            Map<String, String> dataSourceMap = new HashMap<>();
            dataSourceMap.put("driver-class-name", "com.mysql.jdbc.Driver");
            dataSourceMap.put("url", "jdbc:mysql://116.204.77.182:3306/fintech");
            dataSourceMap.put("username", "root");
            dataSourceMap.put("password", "qq152230");
            dataSourceMap.put("minIdle", "1");
            dataSourceMap.put("initialSize", "1");
            ds = DruidDataSourceFactory.createDataSource(dataSourceMap);
        } catch (Exception e) {
            LOGGER.error("数据源初始化失败：{}", e.getMessage());
        }
        DB = new Db(ds);
        if (DB == null) {
            LOGGER.error("数据库初始化失败：未知原因");
        }
        LOGGER.info("--------------数据库初始化结束-----------------");
    }

    public static Object query(String sql) {
        try {
            return DB.query(sql);
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
        return null;
    }

    public static int execute(String sql) {
        try {
            return DB.execute(sql);
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
        return 0;
    }
}
