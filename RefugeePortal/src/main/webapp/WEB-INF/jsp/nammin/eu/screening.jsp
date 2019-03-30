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
			<h2>Hasierako osasun-txekeak</h2>
			<div class="input-radio">
				<p>Alergia al duzu?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>Erretzen al duzu?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">Bai</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">Ez</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>Ba al dago gosea anormala?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<h2>Barne Medikuntza</h2>
			<div class="input-radio">
				<p>Sintomak subjektiboak al dituzu?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">Ez</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Noiz sintomak subjektiboak dituzu?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Zer dira sintomak?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> Berriki kontsulta zaizu?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Ospitalizazio kirurgia jaso al duzu inoiz?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">Ez</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Zer da gaixotasunaren izena garai hartan?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>Baduzu medikuntza edaten ari zaren?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<h2>BGBK</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>Zenbat zigarro egunean erretzen duzu? <br/> (Liburua / eguna)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>Sarritan joaten zara goizean igotzen ostean?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Sarritan sugeak dituzu (Zezei, Hugh Hugh)?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<h2>Medikuntza respiratoria</h2>
			<div class="input-radio">
				<p>Duela 3 hilabeteetan snoreed duzu?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Zure familiarekin kontatu al zaizu azken 3 hilabeteetan lo egiteko arnasketa gelditu dela?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Uste al litzaidake lo lo egin eta lo egin edo lo egin egun edo azken 3 hilabeteetan?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<h2>Diabetes</h2>
			<div class="input-radio">
				<p>Egarri erraza da?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Ba al dakizu zure odoleko azukre maila altua dela?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Zure familia diabetea da?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">Bai</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">Ez</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('Galderazko edukiak zuzendu ditugu.');">Zuzenketa</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>