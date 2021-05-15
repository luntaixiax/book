package com.luntai.dao.impl;

import com.luntai.dao.UserDao;
import com.luntai.pojo.User;

public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public User queryUserByUsername(String username) {
        String sql = "SELECT `id`, `username`, `password`, `email` FROM t_user WHERE username = ?";
        return queryForOne(User.class, sql, username);
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT `id`, `username`, `password`, `email` FROM t_user WHERE username = ? AND password = ?";
        return queryForOne(User.class, sql, username, password);
    }

    @Override
    public int saveUser(User user) {
        String sql = "INSERT INTO t_user(`username`,`password`,`email`) VALUES (?,?,?)";
        return update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}
