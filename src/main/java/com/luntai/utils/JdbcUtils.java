package com.luntai.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataPool;
    private static ThreadLocal<Connection> connThread = new ThreadLocal<>();

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
        Connection conn = connThread.get();
        if (conn == null) {
            try {
                conn = dataPool.getConnection();
                connThread.set(conn); // save to threadLocal to share this conn
                // database affair management
                conn.setAutoCommit(false);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return conn;
    }

    public static void commitAndClose() {
        Connection conn = connThread.get();
        if (conn != null) {
            // means conn has been used before
            try {
                conn.commit();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        connThread.remove();  // prevent Tomcat error, because Tomcat has thread pool
    }

    public static void rollbackAndCLose() {
        Connection conn = connThread.get();
        if (conn != null) {
            // means conn has been used before
            try {
                conn.rollback();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        connThread.remove();  // prevent Tomcat error, because Tomcat has thread pool
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
