package com.luntai.service;

import com.luntai.pojo.Cart;
import com.luntai.pojo.Order;
import com.luntai.pojo.OrderItem;
import com.luntai.pojo.Page;

import java.util.List;

public interface OrderService {

    public String createOrder(Cart cart, Integer userId);

    public Order getOrderByOrderId(String orderId);

    public List<Order> showAllOrders();

    public Page<Order> showAllOrdersPage(int pageNo, int pageSize);

    public List<Order> showMyOrders(Integer userId);

    public Page<Order> showMyOrdersPage(Integer userId, int pageNo, int pageSize);

    public void sendOrder(String orderId);

    public void receiveOrder(String orderId);

    public List<OrderItem> showOrderDetail(String orderId);
}
