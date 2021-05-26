<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Order Manager</title>
		<%@ include file="/pages/common/head.jsp" %>
	</head>

	<body>
		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">Order Manager</span>
			<%@include file="/pages/common/manager_menu.jsp"%>
		</div>

		<div id="main">
			<table>
				<tr>
					<td>Date</td>
					<td>User</td>
					<td>Price</td>
					<td>Status</td>
					<td>Action</td>

				</tr>

				<c:forEach items="${requestScope.page.items}" var="order">
					<tr>
						<td>${order.createTime}</td>
						<td>${order.userId}</td>
						<td>${order.price}</td>
						<td><a href="orderServlet?action=showOrderDetail&orderId=${order.orderId}&pageNo=${requestScope.page.pageNo}">Details</a></td>
						<c:if test="${order.status == 0}">
							<td><a href="orderServlet?action=sendOrder&orderId=${order.orderId}">Dispatch</a></td>
						</c:if>
						<c:if test="${order.status == 1}">
							<td>Shipped</td>
						</c:if>
						<c:if test="${order.status == 2}">
							<td>Delivered</td>
						</c:if>

					</tr>
				</c:forEach>

			</table>

			<%--static include page navigator--%>
			<%@include file="/pages/common/page_nav.jsp"%>

		</div>

		<%@include file="/pages/common/footer.jsp"%>

	</body>
</html>