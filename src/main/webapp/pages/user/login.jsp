<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Luntai.com Login Page</title>
		<%@ include file="/pages/common/head.jsp" %>
	</head>

	<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

		<div class="login_banner">

			<div id="l_content">
				<span class="login_word">Welcome Login</span>
			</div>

			<div id="content">
				<div class="login_form">
					<div class="login_box">
						<div class="tit">
							<h1>Luntai.com</h1>
							<a href="pages/user/regist.jsp">Register Now</a>
						</div>
						<div class="msg_cont">
							<b></b>
							<span class="errorMsg">
								${empty requestScope.msg ? "Enter username and Password" : requestScope.msg}
							</span>
						</div>

						<div class="form">
							<form action="userServlet" method="post">
								<%--add this hidden field to be part of URL parameter (http://ip:port/project/userServlet?action=login&....)--%>
								<input type="hidden" name="action" value="login">

								<label>Username: </label>
								<input class="itxt" type="text" placeholder="Input Username" autocomplete="off" tabindex="1" name="username" value="${requestScope.username}"/>
								<br />
								<br />
								<label>Password: </label>
								<input class="itxt" type="password" placeholder="Input Password" autocomplete="off" tabindex="1" name="password" />
								<br />
								<br />
								<input type="submit" value="Login" id="sub_btn" />
							</form>
						</div>

					</div>
				</div>
			</div>
		</div>

		<%@include file="/pages/common/footer.jsp"%>

	</body>
</html>