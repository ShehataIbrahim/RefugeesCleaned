<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Locale locale = request.getLocale();
	ResourceBundle msgBundle = ResourceBundle.getBundle("Messages", locale);
	String errorMessageCode = request.getAttribute("errorMessage") == null ? null
			: request.getAttribute("errorMessage").toString();
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"></meta>
<title><%=msgBundle.getString("login.title")%></title>
<meta http-equiv="pragma" content="no-cache"></meta>
<meta http-equiv="cache-control" content="no-cache"></meta>
<meta http-equiv="expires" content="0"></meta>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="/static/css/style.login.min.ar.css"></link>
<!-- jQuery -->
<script src="/static/js/vendors/jquery-1.11.2.min.js"></script>
</head>
<body>
	<div class="document">
		<main class="page"> <section class="login-page"
			style="background-color: #fafaf9;">
		<div class="wrapper">
			<div class="login-page__image" style="background-color: #d6e8f6;">
			</div>
			<div class="login-page__form">


				<form class="form-horizontal" id="loginform" action="/user/login"
					method="post">
					<div class="login-item">
						<input type="text" class="form-control" name="username"
							id="username"
							placeholder='<%=msgBundle.getString("login.username")%>' />
					</div>
					<div class="login-item">
						<input type="password" name="password" id="password"
							autocomplete="off" class="form-control"
							placeholder='<%=msgBundle.getString("login.password")%>' />
					</div>
					<%
						if (errorMessageCode != null) {
					%>
					<div class="login-item login-item--remember" style="color:red!important">
						<div><%=msgBundle.getString(errorMessageCode)%></div>
					</div>
					<%
						}
					%>
					<div class="login-item login-item--buttons clearfix">
						<input type="submit"
							class="btn pull-left flip submit-button login-btn"
							value="<%=msgBundle.getString("login.title")%>" />
					</div>
				</form>
			</div>
		</div>
		</section> </main>
		<div class="hidden-xs hidden-sm">
			<br /> <br /> <br /> <br />
		</div>
		<script src="/static/js/vendors/jquery.form-validator.min.js"></script>
	</div>
</body>
</html>