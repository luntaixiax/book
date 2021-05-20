package com.luntai.service;

import com.luntai.pojo.Cart;

public interface OrderService {

    public String createOrder(Cart cart, Integer userId);
}
