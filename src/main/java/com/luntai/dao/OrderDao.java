package com.luntai.dao;

import com.luntai.pojo.Order;

import java.util.List;

public interface OrderDao {

    public int saveOrder(Order order);

    public Order queryOrderByOrderId(String orderId);

    // query orders fro specific user method group
    public List<Order> queryOrdersByUserId(Integer userId);

    public Integer queryOrdersByUserIdPageTotalCount(Integer userId);

    public List<Order> queryOrdersByUserIdPageItems(Integer userId, int begin, int pageSize);

    // query orders method group
    public List<Order> queryOrders();

    public Integer queryOrdersPageTotalCount();

    public List<Order> queryOrdersPageItems(int begin, int pageSize);

    public int changeOrderStatus(String orderId, Integer status);

}
