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
					<td>Info</td>
				</tr>

				<c:forEach items="${requestScope.myOrders}" var="order">
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
						<td><a href="#">Details</a></td>
					</tr>
				</c:forEach>
			</table>

		</div>

		<%@include file="/pages/common/footer.jsp"%>

</body>
</html>