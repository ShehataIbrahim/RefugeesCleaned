<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
</head>
<body>
Add Doctor <br>
<form action="/debug" method="POST">
id:<input type="text" name="id"> <br>
pw:<input type="text" name="pass"> <br>
<input type="hidden" name="mode" value="add_doctor">
<input type="submit">
</form>
<hr>
Password API<br>
<form action="/api/password" method="POST">
<input type="text" name="password">
<input type="submit">
</form>
<hr>
hitachi-fbbot.net
</body>
</html>