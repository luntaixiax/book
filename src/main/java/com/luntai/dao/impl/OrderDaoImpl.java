package com.luntai.dao.impl;

import com.luntai.dao.OrderDao;
import com.luntai.pojo.Order;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO t_order(`order_id`, `create_time`, `price`, `status`, `user_id`) VALUES(?,?,?,?,?)";
        return update(sql, order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());
    }

    @Override
    public List<Order> queryOrdersByUserId(String userId) {
        String sql = "SELECT `order_id` AS orderId, `create_time` AS createTime, `price`, `status`, `user_id` AS userId FROM t_order WHERE `user_id` = ?";
        return queryForList(Order.class, sql, userId);
    }
}
