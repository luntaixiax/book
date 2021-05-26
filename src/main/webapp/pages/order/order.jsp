<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>My Order</title>
		<%@ include file="/pages/common/head.jsp" %>

		<style type="text/css">
			h1 {
				text-align: center;
				margin-top: 200px;
			}
		</style>
	</head>

	<body>

		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">Orders</span>
			<%@ include file="/pages/common/login_success_menu.jsp"%>
		</div>

		<div id="main">

			<table>
				<tr>
					<td>Date</td>
					<td>Amount</td>
					<td>Status</td>
					<td colspan="2">Action</td>
				</tr>

				<c:forEach items="${requestScope.page.items}" var="order">
					<c:choose>
						<c:when test="${order.status == 0}">
							<c:set var="stat" value="Pending"/>
						</c:when>
						<c:when test="${order.status == 1}">
							<c:set var="stat" value="Shipped"/>
						</c:when>
						<c:when test="${order.status == 2}">
							<c:set var="stat" value="Delivered"/>
						</c:when>
						<c:otherwise>
							<c:set var="stat" value="Unknown"/>
						</c:otherwise>
					</c:choose>

					<tr>
						<td>${order.createTime}</td>
						<td>${order.price}</td>
						<td>${stat}</td>
						<td><a href="orderServlet?action=showOrderDetail&orderId=${order.orderId}&pageNo=${requestScope.page.pageNo}">Details</a></td>
						<c:if test="${order.status eq 1}">
							<td><a href="orderServlet?action=receiveOrder&orderId=${order.orderId}">Confirm</a></td>
						</c:if>
						<c:if test="${order.status ne 1}">
							<td></td>
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