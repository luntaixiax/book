<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>尚硅谷会员注册页面</title>
		<%@ include file="/pages/common/head.jsp" %>

		<script type="text/javascript">
			$(function(){
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
				<span class="login_word">欢迎注册</span>
			</div>

			<div id="content">
				<div class="login_form">
					<div class="login_box">
						<div class="tit">
							<h1>注册尚硅谷会员</h1>
							<span class="errorMsg"></span>
						</div>
						<div class="form">
							<form action="registerServlet" method="post">
								<label>用户名称：</label>
								<input class="itxt" type="text" placeholder="请输入用户名"
									   autocomplete="off" tabindex="1" name="username" id="username" />
								<br />
								<br />
								<label>用户密码：</label>
								<input class="itxt" type="password" placeholder="请输入密码"
									   autocomplete="off" tabindex="1" name="password" id="password" />
								<br />
								<br />
								<label>确认密码：</label>
								<input class="itxt" type="password" placeholder="确认密码"
									   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
								<br />
								<br />
								<label>电子邮件：</label>
								<input class="itxt" type="text" placeholder="请输入邮箱地址"
									   autocomplete="off" tabindex="1" name="email" id="email" />
								<br />
								<br />
								<label>验证码：</label>
								<input class="itxt" type="text" style="width: 150px;" name="code" id="code"/>
								<img alt="" src="static/img/code.bmp" style="float: right; margin-right: 40px">
								<br />
								<br />
								<input type="submit" value="注册" id="sub_btn" />

							</form>
						</div>

					</div>
				</div>
			</div>
		</div>

		<%@include file="/pages/common/footer.jsp"%>

	</body>
</html>