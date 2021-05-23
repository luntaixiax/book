<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luntaixia
  Date: 2021/5/14
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Luntai.Book</title>
    <%@ include file="/pages/common/head.jsp" %>

    <script type="text/javascript">
        $(function () {
            $("button.addToCart").click(function () {
                var bookId = $(this).attr("bookId"); // this refers to <button class="addToCart" bookId="xxx"> tag
                <%-- location.href = "${basePath}cartServlet?action=addItem&id=" + bookId;--%>

                $.getJSON(
                    "${basePath}cartServlet",   // url
                    "action=ajaxAddItem&id=" + bookId,  // request data
                    function (data) {
                        $(".cartTotalCount").text("There are " + data.totalCount + "items in your cart");
                        $(".cartLastName").text(data.lastName);  // from server json
                    }
                )

            });
        })
    </script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" >
    <span class="wel_word">Books</span>
    <div>
        <c:if test="${empty sessionScope.user}">
            <%--if user has login--%>
            <a href="pages/user/login.jsp">Login</a> |
            <a href="pages/user/regist.jsp">Register</a> &nbsp;&nbsp;
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <span>Welcome<span class="um_span">${sessionScope.user.username}</span>BookStore</span>
            <a href="pages/order/order.jsp">Orders</a>
            <a href="userServlet?action=logout">Logout</a>
        </c:if>


        <a href="pages/cart/cart.jsp">Cart</a>
        <a href="pages/manager/manager.jsp">Manage</a>
    </div>
</div>
<div id="main">
    <div id="book">
        <div class="book_cond">
            <form action="client/bookServlet" method="get">
                <input type="hidden" name="action" value="pageByPrice">
                Price: $<input id="min" type="text" name="min" value="${param.min}"> -
                $<input id="max" type="text" name="max" value="${param.max}">
                <input type="submit" value="Filter" />
            </form>
        </div>
        <div style="text-align: center">

            <c:if test="${empty sessionScope.cart.items}">
                <span class="cartTotalCount"></span>
                <div>
                    <span class="cartLastName" style="color: red">Nothing in your cart</span>
                </div>
            </c:if>

            <c:if test="${not empty sessionScope.cart.items}">
                <span class="cartTotalCount">There are ${sessionScope.cart.totalCount} items in your cart</span>
                <div>
                    You just added <span class="cartLastName" style="color: red">${sessionScope.lastName}</span> to the cart
                </div>
            </c:if>

        </div>

        <c:forEach items="${requestScope.page.items}" var="book">
            <div class="b_list">
                <div class="img_div">
                    <img class="book_img" alt="" src="${book.imgPath}" />
                </div>
                <div class="book_info">
                    <div class="book_name">
                        <span class="sp1">Title:</span>
                        <span class="sp2">${book.name}</span>
                    </div>
                    <div class="book_author">
                        <span class="sp1">Author:</span>
                        <span class="sp2">${book.author}</span>
                    </div>
                    <div class="book_price">
                        <span class="sp1">Price:</span>
                        <span class="sp2">$${book.price}</span>
                    </div>
                    <div class="book_sales">
                        <span class="sp1">Sales:</span>
                        <span class="sp2">${book.sales}</span>
                    </div>
                    <div class="book_amount">
                        <span class="sp1">Stock:</span>
                        <span class="sp2">${book.stock}</span>
                    </div>
                    <div class="book_add">
                        <button bookId="${book.id}" class="addToCart">Add to Cart</button>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>

    <%@include file="/pages/common/page_nav.jsp"%>

</div>

<%@include file="/pages/common/footer.jsp"%>
</body>
</html>
