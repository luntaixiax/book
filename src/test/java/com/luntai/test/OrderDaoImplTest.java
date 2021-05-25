package com.luntai.test;

import com.luntai.dao.OrderDao;
import com.luntai.dao.impl.OrderDaoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoImplTest {

    public OrderDao orderDao = new OrderDaoImpl();

    @Test
    void queryOrdersByUserId() {
        System.out.println(orderDao.queryOrdersByUserId(1));
    }

    @Test
    void changeOrderStatus() {
        orderDao.changeOrderStatus("16215458794821", 2);
    }
}