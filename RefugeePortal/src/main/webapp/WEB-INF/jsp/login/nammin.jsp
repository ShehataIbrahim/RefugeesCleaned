<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, viewport-fit=cover">
<meta name="format-detection" content="telephone=no">
<meta name="robots" content="noindex, nofollow">
<title>Migrants and Refugee Suport Portal</title>
<link href="/css/html5-doctor-reset-stylesheet.min.css" rel="stylesheet">
<link href="/css/mobile-style-basic.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
	<div class="header header-no-btn" id="login-header">
		<h1>Migrants and Refugee Suport Portal</h1>
	</div>

	<div id="medical-inquiry-data">
		<form action="/login/nammin" method="POST" id="" name="login_form">
			<p class="input-vertical adjustment-bottom-margin"><label>ID</label><input type="text" name="user" id=""></p>
			<p class="input-vertical adjustment-bottom-margin"><label>PASS</label><input type="text" name="password" id=""></p>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:login_form.submit()" onclick="">login</a></p>
		</form>
	</div>
</div>
<script>
<% if(request.getAttribute("retry") != null){ %>
    alert("Login Failed");
<% } %>
</script>
</body>
</html>