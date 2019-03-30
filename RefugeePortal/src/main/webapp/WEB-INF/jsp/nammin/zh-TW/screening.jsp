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
			<h2>初始健康檢查</h2>
			<div class="input-radio">
				<p>你有過敏嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>你抽煙嗎？</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">是的</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">沒有</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>有不正常的食慾嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<h2>內科</h2>
			<div class="input-radio">
				<p>你有主觀症狀嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">是的</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">沒有</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>你什麼時候有主觀症狀？</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>有什麼症狀？</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> 你最近諮詢過了嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>你曾經接受過住院手術嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">是的</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">沒有</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>那時該病的名稱是什麼？</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>你正在喝什麼藥？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<h2>慢性阻塞性肺病</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>你一天抽多少支香煙？ <br/> （書/天）</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>早上起床後你經常會卡住嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>你經常有喘息（Zezei，Hugh Hugh）嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<h2>呼吸器內科</h2>
			<div class="input-radio">
				<p>你在過去的3個月裡打鼾嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>你的家人告訴你過去3個月睡眠時呼吸可能停止嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>您是否在白天或過去3個月感到困倦，睡覺或入睡？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<h2>糖尿病</h2>
			<div class="input-radio">
				<p>口渴容易嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>你有沒有被告知你的血糖水平很高？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>你的家人患有糖尿病嗎？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">是的</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">沒有</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('我們更正了查詢內容。');">更正</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>