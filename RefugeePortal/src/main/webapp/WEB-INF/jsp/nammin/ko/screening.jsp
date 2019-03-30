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
			<h2>첫 건강 검진</h2>
			<div class="input-radio">
				<p>알레르기가 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>담배를 피우십니까?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">예</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">아니오</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>식욕의 이상이 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<h2>내과학</h2>
			<div class="input-radio">
				<p>자각 증상이 있나요?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">예</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">아니오</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>자각 증상은 언제부터인가요?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>어떤 증상이 있나요?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> 최근 진찰을받은 적이 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>지금까지 입원 수술을받은 적이 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">예</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">아니오</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>그 때의 병명은 무엇입니까?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>현재 복용하고있는 약이 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<h2>만성 폐쇄성 폐 질환</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>하루에 몇개 정도 담배를 피우십니까? <br/> (본 / 일)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>아침에 일어나 자마자 땅이 얽히는 일이 자주 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>천명 (제이제이, 휘유)가 자주 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<h2>호흡기 의학</h2>
			<div class="input-radio">
				<p>이 3 개월 동안 코를 곯고습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>이 3 개월 동안 수면 중 호흡이 멈추는 일이 있으면 가족에서 말한 것이 있었습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>이 3 개월간 주간 또는 일어나고있는 것이다 시간대에 졸음이 강해 자고 버릴 것 같아하거나 졸고 적이 있었습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<h2>당뇨병</h2>
			<div class="input-radio">
				<p>목이 갈증 쉽게 사용할 수 있나요?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>혈당치가 높다고 적이 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>가족에 당뇨병 분은 있습니까?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">예</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">아니오</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('문진 내용을 수정했습니다.');">수정</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>