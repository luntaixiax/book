package com.luntai.service.impl;

import com.luntai.dao.BookDao;
import com.luntai.dao.OrderDao;
import com.luntai.dao.OrderItemDao;
import com.luntai.dao.impl.BookDaoImpl;
import com.luntai.dao.impl.OrderDaoImpl;
import com.luntai.dao.impl.OrderItemDaoImpl;
import com.luntai.pojo.*;
import com.luntai.service.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        String orderId = System.currentTimeMillis() + "" + userId; // timestamp+userId to ensure unique orderId
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);
        this.orderDao.saveOrder(order); // save order to database

        // will also save items in the order(cart) into database
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()){
            CartItem cartItem = entry.getValue(); // get cart item from the cart
            // convert to orderItem object
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            this.orderItemDao.saveOrderItem(orderItem); // save the item(s) into database

            // update book stock
            Book book = this.bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            this.bookDao.updateBook(book);

        }

        // clear the cart when order is created
        cart.clear();

        return orderId;
    }
}
