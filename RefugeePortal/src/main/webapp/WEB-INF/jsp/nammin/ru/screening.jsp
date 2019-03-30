<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<%@ page import="net.hitachifbbot.filter.CSRFFilter" %>
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
<script>
var datas = <%= request.getAttribute("answers") %>;

var controlVal = {
  q1 : datas["1"],
  q2 : datas["3"],
  q3 : datas["4"],
  q4 : datas["5"],
  q5 : datas["6"],
  q6 : datas["7"],
  q7 : datas["8"],
  q8 : datas["9"],
  q9 : datas["10"],
  q10 : datas["2"],
  q11 : datas["11"],
  q12 : datas["12"],
  q13 : datas["13"],
  q14 : datas["14"],
  q15 : datas["15"],
  q16 : datas["16"],
  q17 : datas["17"],
  q18 : datas["18"],
  q19 : datas["19"],
}

function setup(){
    var qs = (function(c) { return document.querySelector(c); });

    for (control_id in controlVal){
        var control = qs("#" + control_id);
        if(control){
			control.value = controlVal[control_id];
		}else{
			var chkbox = qs("#" + control_id + "_check" + controlVal[control_id]);
			if(chkbox){ chkbox.checked = true; }
		}
    }

    updateControls();
}

function updateControls(){
    var qs = (function(c) { return document.querySelector(c); });

    qs("#q4").disabled = qs("#q3_check2").checked;
    qs("#q5").disabled = qs("#q3_check2").checked;
    qs("#q8").disabled = qs("#q7_check2").checked;
    qs("#q11").disabled = qs("#q10_check2").checked;
}

window.onload = setup;

</script>
</head>
<body>
<div id="wrapper">
	<div class="header header-no-btn" id="header-bg-medical-inquiry-data">
		<h1>Interview</h1>
	</div>

	<div id="medical-inquiry-data">
		<form action="" method="post" id="">
			<h2>Начальная проверка работоспособности</h2>
			<div class="input-radio">
				<p>Есть ли у вас аллергия?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>Вы курите?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">да</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">нет</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>Есть аномальный аппетит?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<h2>Внутренняя медицина</h2>
			<div class="input-radio">
				<p>У вас есть субъективные симптомы?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">да</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">нет</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Когда у вас есть субъективные симптомы?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Каковы симптомы?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> С вами недавно консультировались?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Вы когда-нибудь получали госпитализацию?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">да</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">нет</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Как называется болезнь в то время?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>Есть ли какое-нибудь лекарство, которое вы сейчас пьете?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<h2>ХОЗЛ</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>Сколько сигарет вы курите в день? <br/> (Книга / день)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>Вы часто застреваете после вставания по утрам?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Часто ли у вас хриплый (Zezei, Hugh Hugh)?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<h2>Дыхательная медицина</h2>
			<div class="input-radio">
				<p>Вы храпились за последние 3 месяца?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Вам сообщили, что ваша душа может остановиться во время сна в течение последних 3 месяцев?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Вы чувствовали сонливость, сон или засыпание днем ​​или в течение последних 3 месяцев?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<h2>диабет</h2>
			<div class="input-radio">
				<p>Легко ли пить?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Вам сказали, что уровень сахара в крови высок?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>У вашей семьи диабет?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">да</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">нет</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('Мы скорректировали содержание справки.');">коррекция</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>