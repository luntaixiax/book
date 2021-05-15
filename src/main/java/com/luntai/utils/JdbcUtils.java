package com.luntai.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataPool;

    static {
        // connection pool initialization, static method
        // Always put jdbc.properties under src/main/resources folder. idea will copy it to target/classes/

        try {
            // load properties config file
            ClassLoader classLoader = JdbcUtils.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("jdbc.properties");

            // load to property container
            Properties properties = new Properties();
            properties.load(inputStream);

            // create pool
            dataPool = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = dataPool.getConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
