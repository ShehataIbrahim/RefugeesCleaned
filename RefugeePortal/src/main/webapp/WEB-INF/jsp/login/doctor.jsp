<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, viewport-fit=cover">
<meta name="format-detection" content="telephone=no">
<meta name="robots" content="noindex, nofollow">
<link href="/css/html5-doctor-reset-stylesheet.min.css" rel="stylesheet">
<link href="/css/pc-style-basic.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
	<header>
		<div>
			<h1><a href="javascript:void(0)">Migrants and Refugee Suport Portal (Doctors)</a></h1>
		</div>
	</header>
	<section>
		<form action="/login/doctor" name="login_form" method="post" id="login">
			<div>
				<p><label>ID：</label><input type="text" name="user"></p>
				<p><label>PASS：</label><input type="password" name="password"></p>
			</div>
			<p class="btn-large" style="text-align:center"><a href="javascript:login_form.submit()" onclick="">Login</a></p>
		</form>
	</section>
</div>
<script>
<% if(request.getAttribute("retry") != null){ %>
    alert("Login Failed");
<% } %>
</script>
</body>
</html>