package com.luntai.test;

import com.luntai.dao.OrderItemDao;
import com.luntai.dao.impl.OrderItemDaoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemDaoImplTest {
    public OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    void queryOrderItemsByOrderId() {
        System.out.println(orderItemDao.queryOrderItemsByOrderId("16215458242841"));
    }
}