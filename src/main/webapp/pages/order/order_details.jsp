<%--
  Created by IntelliJ IDEA.
  User: luntaixia
  Date: 2021-05-26
  Time: 4:06 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Detail</title>
    <%@ include file="/pages/common/head.jsp" %>

    <style>
        #return_link {
            text-align: center;
        }
    </style>


</head>

<body>

    <div id="header">
        <img class="logo_img" alt="" src="static/img/logo.gif" >
        <span class="wel_word">Order Detail</span>
        <%@ include file="/pages/common/login_success_menu.jsp"%>
    </div>

    <div id="main">
        <div class="cart_info">
            <span class="cart_span">Order Id $<span class="b_price">${requestScope.order.orderId}</span></span>
            <span class="cart_span">Subtotal $<span class="b_price">${requestScope.order.price}</span></span>
        </div>

        <table>
            <tr>
                <td>Item</td>
                <td>Quant</td>
                <td>Price</td>
                <td>Amount</td>
            </tr>

            <c:forEach items="${requestScope.orderItems}" var="item">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.count}</td>
                    <td>${item.price}</td>
                    <td>${item.totalPrice}</td>
                </tr>
            </c:forEach>
        </table>

<%--        <span><a href="orderServlet?action=showMyOrdersPage&pageNo=${param.pageNo}">Return</a></span>--%>
        <div id="return_link"><span><a href="${header.referer}">Return</a></span></div>

    </div>

<%@include file="/pages/common/footer.jsp"%>

</body>
</html>
