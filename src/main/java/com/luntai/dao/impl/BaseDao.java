package com.luntai.dao.impl;

import com.luntai.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * use to insert/update/delete
     * @param sql   sql template with ? for params
     * @param args  args to replace ? in sql
     * @return  -1 for failure and other for success
     */
    public int update(String sql, Object... args){
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.update(conn, sql, args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JdbcUtils.close(conn);
        }
        return -1;
    }

    /**
     * query and return a javaBean object
     * @param type  return type of bean (table ORM object)
     * @param sql   sql template with ? for params
     * @param args  args to replace ? in sql
     * @param <T>   type of table ORM
     * @return  T type table object
     */
    public <T> T queryForOne(Class<T> type, String sql, Object... args){
        Connection conn = JdbcUtils.getConnection();
        BeanHandler<T> tBeanHandler = new BeanHandler<>(type);
        try {
            return queryRunner.query(conn, sql, tBeanHandler, args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JdbcUtils.close(conn);
        }
        return null;
    }

    /**
     * query and return list of javaBean object
     * @param type  return type of bean (table ORM object)
     * @param sql   sql template with ? for params
     * @param args  args to replace ? in sql
     * @param <T>   type of table ORM
     * @return  list of T type table objects
     */
    public <T> List<T> queryForList(Class<T> type, String sql, Object... args){
        Connection conn = JdbcUtils.getConnection();
        BeanListHandler<T> tBeanListHandler = new BeanListHandler<>(type);
        try {
            return queryRunner.query(conn, sql, tBeanListHandler, args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.close(conn);
        }
        return null;
    }

    /**
     * return one sql query object
     * @param sql   sql template with ? for params
     * @param args  args to replace ? in sql
     * @return  table object
     */
    public Object queryForSingleValue(String sql, Object... args){
        Connection conn = JdbcUtils.getConnection();
        ScalarHandler scalarHandler = new ScalarHandler();
        try {
            return queryRunner.query(conn, sql, scalarHandler, args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JdbcUtils.close(conn);
        }
        return null;
    }

}
