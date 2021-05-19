<%--
  Created by IntelliJ IDEA.
  User: luntaixia
  Date: 2021/5/14
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <span>Welcome<span class="um_span">${sessionScope.user.username}</span>BookStore</span>
    <a href="pages/order/order.jsp">Orders</a>
    <a href="userServlet?action=logout">Logout</a>&nbsp;&nbsp;
    <a href="index.jsp">MainPage</a>
</div>
