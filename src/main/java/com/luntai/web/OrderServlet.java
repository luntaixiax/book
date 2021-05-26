package com.luntai.web;

import com.luntai.pojo.*;
import com.luntai.service.OrderService;
import com.luntai.service.impl.OrderServiceImpl;
import com.luntai.utils.WebUtils;

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
        // step1. get orders from order service
        List<Order> orders = this.orderService.showAllOrders();

        // step2. save to request
        request.setAttribute("allOrders", orders);

        // step3. dispatch to order management page
        request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request, response);
    }

    protected void showAllOrdersPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter (pageNo and pageSize)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        // step2: call orderService to list orders on pages
        Page<Order> page = this.orderService.showAllOrdersPage(pageNo, pageSize);  // it will find the correspond page (at pagNo)
        page.setUrl("orderServlet?action=showAllOrdersPage"); // set url to the page object so that the frontend can render this to html action

        // step3: save orders to request for frontend to render
        request.setAttribute("page", page);

        // step4: dispatch request to order_manager page (main page of order manager)
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

    protected void showMyOrdersPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: get request parameter (pageNo and pageSize)
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        // step2. get userId from session
        User loginUser = (User) request.getSession().getAttribute("user"); // get user from session
        Integer userId = loginUser.getId();

        // step3. get orders from order service
        Page<Order> page = this.orderService.showMyOrdersPage(userId, pageNo, pageSize);
        page.setUrl("orderServlet?action=showMyOrdersPage"); // set url to the page object so that the frontend can render this to html action

        // step4: save orders to request for frontend to render
        request.setAttribute("page", page);

        // step5: dispatch request to order page (main page of user orders)
        request.getRequestDispatcher("/pages/order/order.jsp").forward(request, response);

    }

    protected void sendOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get orderId from URL request
        String orderId = request.getParameter("orderId");

        // step2. call orderService to do delivery
        this.orderService.sendOrder(orderId);

        // step3. redirect to current page
        // redirect to referer url
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void receiveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get orderId from URL request
        String orderId = request.getParameter("orderId");

        // step2. call orderService to do delivery
        this.orderService.receiveOrder(orderId);

        // step3. redirect to current page
        // redirect to referer url
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void showOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1. get orderId from URL request
        String orderId = request.getParameter("orderId");

        // step2. call orderService to query order items and save to request
        List<OrderItem> orderItems = this.orderService.showOrderDetail(orderId);
        Order order = this.orderService.getOrderByOrderId(orderId);
        request.setAttribute("orderItems", orderItems);
        request.setAttribute("order", order);

        // step3. dispatch to order detail page
        request.getRequestDispatcher("/pages/order/order_details.jsp").forward(request, response);
    }

}
