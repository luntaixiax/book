<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Book Management</title>
		<%@ include file="/pages/common/head.jsp" %>

		<script type="text/javascript">
			$(function (){
				$("a.deleteClass").click(function (){
					<%-- 	this refers to active dom object--%>
					<%-- 	this = <a class="deleteClass">, this.parent() = <td>, this.parent().parent() = <tr>		--%>
					<%--	.find("td:first") = find first <td> context under <tr>  ====>  <td>${book.name}</td>	--%>
					<%--	.text() = content text of this <td>  ====>  ${book.name}  ===> list the name of the book	--%>
					var obj = $(this).parent().parent().find("td:first").text()  // the name of the book
					return confirm("Are you sure to delete【" + obj + "】?");
				})
			})
		</script>
	</head>
	<body>

		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">Book Manager</span>
			<%@include file="/pages/common/manager_menu.jsp"%>
		</div>

		<div id="main">
			<table>
				<tr>
					<td>Title</td>
					<td>Price</td>
					<td>Author</td>
					<td>Sales</td>
					<td>Stock</td>
					<td colspan="2">Operation</td>
				</tr>

				<c:forEach items="${requestScope.page.items}" var="book">
					<tr>
						<td>${book.name}</td>
						<td>${book.price}</td>
						<td>${book.author}</td>
						<td>${book.sales}</td>
						<td>${book.stock}</td>
						<td><a href="manager/bookServlet?method=update&action=getBook&id=${book.id}&pageNo=${requestScope.page.pageNo}">Edit</a></td>
						<td><a class="deleteClass" href="manager/bookServlet?action=delete&id=${book.id}&pageNo=${requestScope.page.pageNo}">Delete</a></td>
					</tr>
				</c:forEach>

				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><a href="pages/manager/book_edit.jsp?method=add&pageNo=${requestScope.page.pageTotal}">Add</a></td>
				</tr>
			</table>

			<%--static include page navigator--%>
			<%@include file="/pages/common/page_nav.jsp"%>

		</div>

		<%@include file="/pages/common/footer.jsp"%>

	</body>
</html>