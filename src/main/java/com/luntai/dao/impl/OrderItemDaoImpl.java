package com.luntai.dao.impl;

import com.luntai.dao.OrderItemDao;
import com.luntai.pojo.OrderItem;

import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO t_order_item(`name`, `count`, `price`, `total_price`, `order_id`) VALUES(?,?,?,?,?)";
        return update(sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(String orderId) {
        String sql = "SELECT `id`, `name`, `count`, `price`, `total_price` AS totalPrice, `order_id` AS orderId FROM t_order_item WHERE `order_id` = ?";
        return queryForList(OrderItem.class, sql, orderId);
    }
}
