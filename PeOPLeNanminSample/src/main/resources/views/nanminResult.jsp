<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main</title>
<!-- Bootstrap使用のためimport -->
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<script type="text/javascript" src="resources/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="resources/js/tether.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/popper.min.js"></script>

<!-- 自作css、jsをimport -->
<link rel="stylesheet" href="resources/css/nanminSample.css">
</head>
<body>
	<form action="/search" method="POST">
		<table>
		<tr><td>Key date<br>（yyyy-mm-dd）</td><td><input type="search" name="ifa_date" size=25% value=${result.ifa_date} ></td><td></td></tr>
		<tr><td>Refugee ID</td><td><input type="search" name="patient_id" size=25% value=${result.patient_id} ></td>
		    <td><button type="submit" name="searchBtn" value="searchBtn">Select</button></td></tr>
		</table>
		<table>
		<tr><td>Name</td><td><input type="search" name="full_name" size=25% value=${result.full_name} ></td><td></td></tr>
		<tr><td>Sex</td><td><input type="search"name="sex" size=25% value=${result.sex} ></td><td></td></tr>
		<tr><td>Birthday</td><td><input type="search" name="birth_day"size=25% value=${result.birth_day} ></td><td></td></tr>
		</table>
		<table>
		<tr><td>Height</td><td><input type="search"name="height" size=25% value=${result.height} ></td><td></td></tr>
		<tr><td>Weight</td><td><input type="search" name="weight"size=25% value=${result.weight} ></td><td></td></tr>
		<tr><td>Blood Type</td><td><input type="search" name="blood_abo"size=25% value=${result.blood_abo} ></td><td></td></tr>
		<tr><td>Systolic blood pressure(Highest)</td><td><input type="search"name="systolic" size=25% value=${result.systolic} ></td><td></td></tr>
		<tr><td>Diastolic blood pressure(Lowest)</td><td><input type="search" name="diastolic" size=25% value=${result.diastolic} ></td><td></td></tr>
		<tr><td>Interview smoking</td><td><input type="search" name="smoking" size=25% value=${result.smoking} ></td><td></td></tr>
		</table>
		<table>
		<tr><td></td><td></td><td><button type="submit" name="addBtn" value="addBtn">Add/Update</button></td></tr>
		<tr><td colspan = "3">${result.msg}</td></tr>
		</table>
	</form>

</body>
</html>