package com.luntai.dao;

import com.luntai.pojo.OrderItem;

import java.util.List;

public interface OrderItemDao {

    public int saveOrderItem(OrderItem orderItem);

    public List<OrderItem> queryOrderItemsByOrderId(String orderId);

}
