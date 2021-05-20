<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cart</title>
		<%@ include file="/pages/common/head.jsp" %>

		<script type="text/javascript">
			$(function () {
				$("a.deleteItem").click(function () {
					return confirm("Are you sure to delete 【" + $(this).parent().parent().find("td:first").text()+ "】?");
				});

				$("#clearCart").click(function () {
					return confirm("Are you sure to clear the cart?");
				});

				$(".updateCart").change(function () {   // when the content changes
					var name = $(this).parent().parent().find("td:first").text();
					var id = $(this).attr("bookId"); // customized attr id
					var count = this.value;  // total number of this item
					if (confirm("Are you sure to buy【" + count + "】of 【" + name + "】?")){
						location.href = "${basePath}cartServlet?action=updateCount&count=" + count + "&id=" + id;
					} else {
						this.value = this.defaultValue; // default value is original value before change
					};
				})
			})
		</script>

	</head>

	<body>

		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">Cart</span>
			<%@ include file="/pages/common/login_success_menu.jsp"%>
		</div>

		<div id="main">

			<table>
				<tr>
					<td>Item</td>
					<td>Quant</td>
					<td>Price</td>
					<td>Amount</td>
					<td>Operation</td>
				</tr>

				<c:if test="${empty sessionScope.cart.items}">
					<tr>
						<td colspan="5"><a href="index.jsp">Nothing in the Cart!</a></td>
					</tr>
				</c:if>

				<c:if test="${not empty sessionScope.cart.items}">
					<c:forEach items="${sessionScope.cart.items}" var="entry">
						<tr>
							<td>${entry.value.name}</td>
							<td><input class="updateCart" bookId="${entry.value.id}" type="text" value="${entry.value.count}" style="width: 50px"></td>
							<td>${entry.value.price}</td>
							<td>${entry.value.totalPrice}</td>
							<td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${entry.value.id}">Delete</a></td>
						</tr>
					</c:forEach>
				</c:if>

			</table>

			<c:if test="${not empty sessionScope.cart.items}">
				<div class="cart_info">
					<span class="cart_span">Items in cart:<span class="b_count">${sessionScope.cart.totalCount}</span></span>
					<span class="cart_span">Subtotal $<span class="b_price">${sessionScope.cart.totalPrice}</span></span>
					<span class="cart_span"><a id = "clearCart" href="cartServlet?action=clear">Clear</a></span>
					<span class="cart_span"><a href="pages/cart/checkout.jsp">Checkout</a></span>
				</div>
			</c:if>

		</div>

		<%@include file="/pages/common/footer.jsp"%>

	</body>
</html>