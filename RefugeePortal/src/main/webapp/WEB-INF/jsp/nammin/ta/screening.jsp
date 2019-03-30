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
			<h2>ஆரம்ப சுகாதார சோதனை</h2>
			<div class="input-radio">
				<p>உங்களுக்கு ஏதாவது ஒவ்வாமை இருக்கிறதா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>நீங்கள் புகைக்கிறீர்களா?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">ஆமாம்</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">இல்லை</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>அசாதாரணமான பசியின்மை இருக்கிறதா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<h2>உள் மருத்துவம்</h2>
			<div class="input-radio">
				<p>உங்களுக்கு அகநிலை அறிகுறிகள் இருக்கிறதா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>நீங்கள் அகநிலை அறிகுறிகள் எப்போது?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>அறிகுறிகள் என்ன?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> சமீபத்தில் நீங்கள் கலந்தாலோசித்தீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>நீங்கள் மருத்துவமனையில் அறுவை சிகிச்சை பெற்றிருக்கிறீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>அந்த நேரத்தில் அந்த நோய்க்கான பெயர் என்ன?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>தற்போது நீங்கள் குடித்துக்கொண்டிருக்கும் எந்தவொரு மருந்து?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<h2>சிஓபிடி</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>ஒரு சிகரெட்டை எத்தனை சிகரெட்டுகள் எடுத்தீர்கள்? <br/> (புத்தகம் / நாள்)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>காலையில் எழுந்தவுடன் நீங்கள் அடிக்கடி சிக்கி இருக்கிறீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>நீங்கள் அடிக்கடி புண்களை (Zezei, ஹக் ஹக்) இருக்கிறீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<h2>சுவாச மருத்துவம்</h2>
			<div class="input-radio">
				<p>கடந்த 3 மாதங்களில் நீங்கள் பாதிக்கப்பட்டுள்ளீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>கடந்த 3 மாதங்களில் சுவாசத்தில் மூச்சு நிறுத்தலாம் என்று உங்கள் குடும்பத்தினர் சொல்லியிருக்கிறீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>நீங்கள் தூக்கம் மற்றும் தூக்கம் அல்லது பகல் நேரத்தில் அல்லது கடந்த 3 மாதங்களில் தூங்கிக்கொண்டிருப்பதை உணர்ந்தீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<h2>நீரிழிவு</h2>
			<div class="input-radio">
				<p>தாகம் எளிதானதா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>உங்கள் இரத்த சர்க்கரை அளவு அதிகமாக உள்ளது என்று நீங்கள் கூறப்பட்டிருக்கிறீர்களா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>உங்கள் குடும்பத்தில் நீரிழிவு உள்ளதா?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">ஆமாம்</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">இல்லை</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('நாங்கள் விசாரணை உள்ளடக்கங்களை சரி செய்தோம்.');">திருத்தம்</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>