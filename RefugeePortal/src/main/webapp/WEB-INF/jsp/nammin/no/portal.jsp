<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<%@ page import="net.hitachifbbot.filter.CSRFFilter" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, viewport-fit=cover">
<meta name="format-detection" content="telephone=no">
<meta name="robots" content="noindex, nofollow">
<title>Migrants and Refugee Suport Portal</title>
<link href="/css/html5-doctor-reset-stylesheet.min.css" rel="stylesheet">
<link href="/css/mobile-style-basic.css" rel="stylesheet">
<style>
	/*「新着情報」の点滅アニメーションのタイミング */
	.news p:first-child {
		-webkit-animation: flash 1.5s infinite linear;
		   -moz-animation: flash 1.5s infinite linear;
			  -ms-animation: flash 1.5s infinite linear;
		        animation: flash 1.5s infinite linear;
	}

	/*「登録が完了しました」の表示(block) or 非表示(none) */
	.news #show-msg { display: block; }
</style>
</head>
<body>
<div id="wrapper">
    <div class="header-top">
         <h1>Migrants and Refugee Suport Portal</h1>
         <form action="/nammin/logout" method="POST" name="form_logout">
         <%= CSRFFilter.getInputTagString(request) %>
 		<p><a href="javascript:confirmLogout()" onclick="">Logg ut</a></p>
 		</form>
    </div>
    <div id="top">
        <div class="news">
            <p>News</p>
            <p id="show-msg">Registreringen er fullført</p>
        </div>
        <a href="/nammin/basicinfo" class="menu-btn">
            <div class="menu-btn-inner btn-01"><p>Personal</p></div>
        </a>
        <% if(request.getAttribute("noScreening") != null){ %>
        <a href="javascript:alert('Vennligst få medisinske henvendelser på Messenger. Hvis du allerede har blitt intervjuet, vennligst last inn.')" class="menu-btn">
        <% }else{ %>
       <a href="/user/authenticated/home" class="menu-btn">
        <% } %>
            <div class="menu-btn-inner btn-02"><p>Interview</p></div>
        </a>
        <a href="/nammin/ncds.html" class="menu-btn">
            <div class="menu-btn-inner btn-03"><p>NCDs</p></div>
        </a>
        <a href="/nammin/mch.html" class="menu-btn">
            <div class="menu-btn-inner btn-04"><p>MCH</p></div>
        </a>
        <a href="javascript:void(0)" class="menu-btn">
            <div class="menu-btn-inner btn-05"><p>Camp information</p></div>
        </a>
    </div>
</div>
<script>
    function confirmLogout(){
        if(!confirm("Vil du logge deg ut?")){
            return false;
        }else{
            form_logout.submit();
        }
    }
</script>
</body>
</html>