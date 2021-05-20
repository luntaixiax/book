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
				<tr>
					<td>2015.04.23</td>
					<td>90.00</td>
					<td>未发货</td>
					<td><a href="#">查看详情</a></td>
				</tr>

				<tr>
					<td>2015.04.20</td>
					<td>20.00</td>
					<td>已发货</td>
					<td><a href="#">查看详情</a></td>
				</tr>

				<tr>
					<td>2014.01.23</td>
					<td>190.00</td>
					<td>已完成</td>
					<td><a href="#">查看详情</a></td>
				</tr>
			</table>

		</div>

		<%@include file="/pages/common/footer.jsp"%>

</body>
</html>