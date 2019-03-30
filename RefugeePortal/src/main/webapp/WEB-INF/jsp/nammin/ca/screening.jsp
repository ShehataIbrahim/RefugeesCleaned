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
			<h2>Revisió inicial de salut</h2>
			<div class="input-radio">
				<p>Té alguna al·lèrgia?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>Fumen?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">Sí</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">No</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>Hi ha un apetit anormal?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<h2>Medicina interna</h2>
			<div class="input-radio">
				<p>Té símptomes subjectius?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">No</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Quan tens símptomes subjectius?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Quins són els símptomes?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> Has estat consultat recentment?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Alguna vegada ha rebut la cirurgia d'hospitalització?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">No</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Quin és el nom de la malaltia en aquest moment?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>Hi ha alguna medicina que estigueu bevent actualment?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<h2>MPOC</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>Quants cigarrets fumen un dia? <br/> (Llibre / dia)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>Sovint et quedes encallat després d'aixecar-te al matí?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Sovint tens wheezy (Zezei, Hugh Hugh)?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<h2>Medicina respiratòria</h2>
			<div class="input-radio">
				<p>Has rosegat en els últims 3 mesos?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Ha estat informat per la seva família que la respiració pot aturar durant el somni durant els últims 3 mesos?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>T'has sentit adormit i dormint o adormit durant el dia o durant els últims 3 mesos?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<h2>Diabetis</h2>
			<div class="input-radio">
				<p>És fàcil tenir set?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>S'ha dit que el nivell de sucre en la sang és alt?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>La seva família té diabetis?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">Sí</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">No</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('Hem corregit els continguts de la consulta.');">Correcció</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>