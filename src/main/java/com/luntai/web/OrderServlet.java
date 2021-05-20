package com.luntai.web;

import com.luntai.pojo.Cart;
import com.luntai.pojo.User;
import com.luntai.service.OrderService;
import com.luntai.service.impl.OrderServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OrderServlet", value = "/orderServlet")
public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get cart and user from session
        Cart cart = (Cart) request.getSession().getAttribute("cart"); // get cart from session
        User loginUser = (User) request.getSession().getAttribute("user"); // get user from session

        // step2. check if the user has login, if not, redirect to login page
        if (loginUser == null) {
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            return;
        }

        // step3. use orderService to create order
        Integer userId = loginUser.getId();
        String orderId = this.orderService.createOrder(cart, userId);
        request.getSession().setAttribute("orderId", orderId); // save orderId to session

        // step4. redirect to checkout page
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");
    }

}
