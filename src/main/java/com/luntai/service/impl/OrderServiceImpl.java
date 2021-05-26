package com.luntai.service.impl;

import com.luntai.dao.BookDao;
import com.luntai.dao.OrderDao;
import com.luntai.dao.OrderItemDao;
import com.luntai.dao.impl.BookDaoImpl;
import com.luntai.dao.impl.OrderDaoImpl;
import com.luntai.dao.impl.OrderItemDaoImpl;
import com.luntai.pojo.*;
import com.luntai.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        String orderId = System.currentTimeMillis() + "" + userId; // timestamp+userId to ensure unique orderId
        Order order = new Order(orderId, LocalDateTime.now(), cart.getTotalPrice(), 0, userId);
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

    @Override
    public Order getOrderByOrderId(String orderId) {
        return this.orderDao.queryOrderByOrderId(orderId);
    }

    @Override
    public List<Order> showAllOrders() {
        return this.orderDao.queryOrders();
    }

    @Override
    public Page<Order> showAllOrdersPage(int pageNo, int pageSize) {
        // return a page object

        // create a page object
        Page<Order> page = new Page<>();
        page.setPageSize(pageSize);

        // find total records
        Integer pageTotalCount = this.orderDao.queryOrdersPageTotalCount();
        // find total number of pages
        Integer pageTotal = pageTotalCount % pageSize > 0 ? pageTotalCount / pageSize + 1 : pageTotalCount / pageSize;

        // !!! must set pageTotal before setPageNo, because in Page.setPageNo(), it will check if current pageNo is greater than pageTotal
        page.setPageTotal(pageTotal);
        page.setPageNo(pageNo);

        // find begin index
        int begin = Math.max((page.getPageNo() - 1), 0) * pageSize;  // use .getPageNo() to prevent pageNo overflow (will cap to pageTotal)
        // get items on current page
        List<Order> items = this.orderDao.queryOrdersPageItems(begin, pageSize);

        // save attributes to page object
        page.setPageTotalCount(pageTotalCount);
        page.setItems(items);

        return page;
    }

    @Override
    public List<Order> showMyOrders(Integer userId) {
        return this.orderDao.queryOrdersByUserId(userId);
    }

    @Override
    public Page<Order> showMyOrdersPage(Integer userId, int pageNo, int pageSize) {
        // return a page object

        // create a page object
        Page<Order> page = new Page<>();
        page.setPageSize(pageSize);

        // find total records
        Integer pageTotalCount = this.orderDao.queryOrdersByUserIdPageTotalCount(userId);
        // find total number of pages
        Integer pageTotal = pageTotalCount % pageSize > 0 ? pageTotalCount / pageSize + 1 : pageTotalCount / pageSize;

        // !!! must set pageTotal before setPageNo, because in Page.setPageNo(), it will check if current pageNo is greater than pageTotal
        page.setPageTotal(pageTotal);
        page.setPageNo(pageNo);

        // find begin index
        int begin = Math.max((page.getPageNo() - 1), 0) * pageSize;  // use .getPageNo() to prevent pageNo overflow (will cap to pageTotal)
        // get items on current page
        List<Order> items = this.orderDao.queryOrdersByUserIdPageItems(userId, begin, pageSize);

        // save attributes to page object
        page.setPageTotalCount(pageTotalCount);
        page.setItems(items);

        return page;
    }

    @Override
    public void sendOrder(String orderId) {
        this.orderDao.changeOrderStatus(orderId, 1); // 1 means shipped
    }

    @Override
    public void receiveOrder(String orderId) {
        this.orderDao.changeOrderStatus(orderId, 2); // 2 means delivered (received)
    }

    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        return this.orderItemDao.queryOrderItemsByOrderId(orderId);
    }
}
