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
    public List<Order> queryOrdersByUserId(Integer userId) {
        String sql = "SELECT `order_id` AS orderId, `create_time` AS createTime, `price`, `status`, `user_id` AS userId FROM t_order WHERE `user_id` = ? ORDER BY createTime DESC";
        return queryForList(Order.class, sql, userId);
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "SELECT `order_id` AS orderId, `create_time` AS createTime, `price`, `status`, `user_id` AS userId FROM t_order ORDER BY createTime DESC";
        return queryForList(Order.class, sql);
    }

    @Override
    public int changeOrderStatus(String orderId, Integer status) {
        String sql = "UPDATE t_order SET `status` = ? WHERE `order_id` = ?";
        return update(sql, status, orderId);
    }
}
