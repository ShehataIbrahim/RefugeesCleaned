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
			<h2>Kev kuaj mob thawj zaug</h2>
			<div class="input-radio">
				<p>Koj puas muaj kev fab tshuaj?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>Koj puas haus luam yeeb?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">Muaj</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">Tsis yog</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>Puas muaj kev tsis txaus siab?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<h2>Internal tshuaj</h2>
			<div class="input-radio">
				<p>Koj puas muaj cov tsos mob tus yam ntxwv?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Thaum twg koj puas muaj cov tsos mob zoo li?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Cov tsos mob zoo li cas?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> Koj puas tau ntsib nrog tsis ntev tas los no?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Koj puas tau txais kev kho mob hauv tsev kho mob?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>Lub npe ntawm tus kab mob ntawm lub sijhawm ntawd yog dab tsi?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>Puas muaj cov tshuaj uas koj tab tom haus tam sim no?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<h2>COPD</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>Ib hnub twg koj haus luam yeeb pes tsawg? <br/> (Phau Ntawv / hnub)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>Puas yog koj feem ntau yuav khoov tawm tom qab sawv ntxov?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Koj puas muaj feem ntau wheezes (Zezei, Hugh Hugh)?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<h2>Cov tshuaj tua kab mob</h2>
			<div class="input-radio">
				<p>Koj puas tau xaus rau hauv 3 lub hlis dhau los?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Koj puas tau hais qhia los ntawm koj tsev neeg tias kev ua pa yuav nres tau thaum pw tsaug zog hauv 3 lub hlis dhau los?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Koj puas hnov ​​pw tsaug zog thiab pw lossis pw tsaug zog thaum nruab hnub lossis 3 lub hlis dhau los?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<h2>Ntshav qab zib</h2>
			<div class="input-radio">
				<p>Nws puas yooj yim rau nqhis dej?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Koj puas tau hais tias koj cov ntshav qab zib siab npaum li cas?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>Koj tsev neeg puas muaj ntshav qab zib?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">Muaj</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">Tsis yog</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('Peb kho cov lus nug.');">Kho</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>