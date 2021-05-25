package com.luntai.web;

import com.luntai.pojo.Cart;
import com.luntai.pojo.Order;
import com.luntai.pojo.User;
import com.luntai.service.OrderService;
import com.luntai.service.impl.OrderServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/orderServlet")
public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get cart and user from session
        Cart cart = (Cart) request.getSession().getAttribute("cart"); // get cart from session
        User loginUser = (User) request.getSession().getAttribute("user"); // get user from session

        // step2. use orderService to create order
        Integer userId = loginUser.getId();
        String orderId = this.orderService.createOrder(cart, userId);
        request.getSession().setAttribute("orderId", orderId); // save orderId to session

        // step3. redirect to checkout page
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");
    }

    protected void showAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: split page
        // step1. get orders from order service
        List<Order> orders = this.orderService.showAllOrders();

        // step2. save to request
        request.setAttribute("allOrders", orders);

        // step3. dispatch to order management page
        request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request, response);
    }

    protected void showMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get userId from session
        User loginUser = (User) request.getSession().getAttribute("user"); // get user from session
        Integer userId = loginUser.getId();

        // step2. get orders from order service
        List<Order> orders = this.orderService.showMyOrders(userId);

        // step3. save to request
        request.setAttribute("myOrders", orders);

        // step4. dispatch to order management page
        request.getRequestDispatcher("/pages/order/order.jsp").forward(request, response);

    }

}
