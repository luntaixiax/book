package com.luntai.dao.impl;

import com.luntai.dao.OrderDao;
import com.luntai.pojo.Order;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO t_order(`order_id`, `create_time`, `price`, `status`, `user_id`) VALUES(?,?,?,?,?)";
        return update(sql, order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());
    }
}
