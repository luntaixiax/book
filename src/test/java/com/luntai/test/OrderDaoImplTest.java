package com.luntai.test;

import com.luntai.dao.OrderDao;
import com.luntai.dao.impl.OrderDaoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoImplTest {

    @Test
    void queryOrdersByUserId() {
        OrderDao orderDao = new OrderDaoImpl();
        System.out.println(orderDao.queryOrdersByUserId("4"));
    }
}