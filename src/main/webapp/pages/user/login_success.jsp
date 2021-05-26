<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Luntai.com Login Page</title>
		<%@ include file="/pages/common/head.jsp" %>

		<style type="text/css">
			h1, h2 {
				text-align: center;
				margin-top: 200px;
			}

			h1 a {
				color:red;
			}
		</style>
	</head>

	<body>
		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<%@ include file="/pages/common/login_success_menu.jsp"%>
		</div>
		
		<div id="main">
			<c:if test="${requestScope.isManager eq false}">
				<h2>Permission Denied, only manager can enter this page!</h2>
			</c:if>
			<h1>Welcome Back <a href="index.jsp">Return to MainPage</a></h1>
	
		</div>

		<%@include file="/pages/common/footer.jsp"%>
	</body>
</html>