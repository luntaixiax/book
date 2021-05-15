package com.luntai.service.impl;

import com.luntai.dao.UserDao;
import com.luntai.dao.impl.UserDaoImpl;
import com.luntai.pojo.User;
import com.luntai.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean exitsUsername(String username) {
        return userDao.queryUserByUsername(username) != null;
    }
}
