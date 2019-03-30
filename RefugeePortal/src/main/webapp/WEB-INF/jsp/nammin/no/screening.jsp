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
			<h2>Innledende helsekontroll</h2>
			<div class="input-radio">
				<p>Har du allergier?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>Røyker du?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">Ja</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">ikke</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>Er det unormal appetitt?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<h2>Intern medisin</h2>
			<div class="input-radio">
				<p>Har du subjektive symptomer?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">ikke</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Når har du subjektive symptomer?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Hva er symptomene?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> Har du blitt konsulent nylig?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Har du noen gang fått sykehusoperasjon?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">ikke</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Hva er navnet på sykdommen på den tiden?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>Er det noen medisiner du drikker for øyeblikket?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<h2>KOLS</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>Hvor mange sigaretter røyker du en dag? <br/> (Bok / dag)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>Får du ofte fast etter å stå opp om morgenen?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Har du ofte wheezes (Zezei, Hugh Hugh)?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<h2>Åndedrettsmedisin</h2>
			<div class="input-radio">
				<p>Har du snurret i de siste 3 månedene?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Har du blitt fortalt av familien din at pusten kan stoppe under søvnen de siste 3 månedene?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Har du trøtt og sovet eller sovnet om dagen eller i løpet av de siste 3 månedene?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<h2>diabetes</h2>
			<div class="input-radio">
				<p>Er det lett å tørke?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Har du blitt fortalt at blodsukkernivået ditt er høyt?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Har familien din diabetes?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">Ja</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">ikke</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('Vi korrigerte undersøkelsesinnholdet.');">korreksjon</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>