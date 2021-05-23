<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Luntai.com Register Page</title>
		<%@ include file="/pages/common/head.jsp" %>

		<script type="text/javascript">
			$(function(){

				// validate user
				$("#username").blur(function () {
					//  get username from form
					var username = this.value;
					// send Ajax request
					$.getJSON(
							"${basePath}userServlet",	// url
							"action=ajaxExistsUsername&username=" + username, // request data
							function (data) {
								// data is from server response json
								if (data.existsUsername) {
									$("span.errorMsg").text("Username Exists!");
								} else {
									$("span.errorMsg").text("Username Available~");
								}
							}
					);


				});

				// click img to change validation code
				$("#code_img").click(function (){
					this.src = "${basePath}/kaptcha.jpg?d=" + new Date(); // add a new date to add randomness to prevent cache
				});

				$("#sub_btn").click(function (){
					//Validate username
					var usernameText = $("#username").val();
					var usernamePatt = /^\w{5,12}$/;
					if(!usernamePatt.test(usernameText)){
						$("span.errorMsg").text("Not Valid Username!!");
						return false;
					}

					//validate password
					var passwordText = $("#password").val();
					var passwordPatt = /^\w{5,12}$/;
					if(!passwordPatt.test(passwordText)){
						$("span.errorMsg").text("Not Valid Password!!");
						return false;
					}

					//confirm password
					var repwdText = $("#repwd").val();
					if(repwdText != passwordText){
						$("span.errorMsg").text("Confirmed password is wrong!!");
						return false;
					}

					//validate email
					var emailText = $("#email").val();
					var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
					if(!emailPatt.test(emailText)){
						$("span.errorMsg").text("Not valid email address!!");
						return false;
					}

					//validate code
					var codeText = $("#code").val();

					codeText = $.trim(codeText); //prevent multiple spaces

					if(codeText == null || codeText == ""){
						//remind the user
						$("span.errorMsg").text("Validation Code Error!!");
						return false;
					}

					$("span.errorMsg").text("");
				});
			});

		</script>

		<style type="text/css">
			.login_form{
				height:420px;
				margin-top: 25px;
			}

		</style>
	</head>

	<body>
		<div id="login_header">`
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

		<div class="login_banner">

			<div id="l_content">
				<span class="login_word">Welcome Register</span>
			</div>

			<div id="content">
				<div class="login_form">
					<div class="login_box">
						<div class="tit">
							<h1>Luntai.com</h1>
							<span class="errorMsg">${requestScope.msg}</span>
						</div>
						<div class="form">
							<form action="userServlet" method="post">
								<%--add this hidden field to be part of URL parameter (http://ip:port/project/userServlet?action=register&....)--%>
								<input type="hidden" name="action" value="register">

								<label>Username: </label>
								<input class="itxt" type="text" placeholder="Input Username"
									   autocomplete="off" tabindex="1" name="username" id="username" value="${requestScope.username}" />
								<br />
								<br />
								<label>Password: </label>
								<input class="itxt" type="password" placeholder="Input password"
									   autocomplete="off" tabindex="1" name="password" id="password" />
								<br />
								<br />
								<label>Confirm: </label>
								<input class="itxt" type="password" placeholder="Confirm password"
									   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
								<br />
								<br />
								<label>Email: </label>
								<input class="itxt" type="text" placeholder="Input email"
									   autocomplete="off" tabindex="1" name="email" id="email" value="${requestScope.email}" />
								<br />
								<br />
								<label>QR code: </label>
								<input class="itxt" type="text" style="width: 80px;" name="code" id="code"/>
								<img id="code_img" alt="" src="kaptcha.jpg" style="float: right; margin-right: 40px; width: 110px; height: 30px">
								<br />
								<br />
								<input type="submit" value="Register" id="sub_btn" />

							</form>
						</div>

					</div>
				</div>
			</div>
		</div>

		<%@include file="/pages/common/footer.jsp"%>

	</body>
</html>