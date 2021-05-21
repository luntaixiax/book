package com.luntai.dao;

import com.luntai.pojo.Order;

import java.util.List;

public interface OrderDao {

    public int saveOrder(Order order);

    public List<Order> queryOrdersByUserId(String userId);

}
