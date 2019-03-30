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
			<h2>ಆರಂಭಿಕ ಆರೋಗ್ಯ ಪರಿಶೀಲನೆ</h2>
			<div class="input-radio">
				<p>ನಿಮಗೆ ಯಾವುದೇ ಅಲರ್ಜಿಗಳು ಇದೆಯೆ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>ನೀವು ಧೂಮಪಾನ ಮಾಡುತ್ತೀರಾ?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">ಹೌದು</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">ಇಲ್ಲ</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>ಅಸಹಜ ಹಸಿವು ಇದೆಯೇ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<h2>ಇಂಟರ್ನಲ್ ಮೆಡಿಸಿನ್</h2>
			<div class="input-radio">
				<p>ನೀವು ವ್ಯಕ್ತಿನಿಷ್ಠ ರೋಗಲಕ್ಷಣಗಳನ್ನು ಹೊಂದಿದ್ದೀರಾ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>ನಿಮಗೆ ವೈಯಕ್ತಿಕ ರೋಗಲಕ್ಷಣಗಳು ಯಾವಾಗ?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>ಲಕ್ಷಣಗಳು ಯಾವುವು?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> ನೀವು ಇತ್ತೀಚೆಗೆ ಸಮಾಲೋಚಿಸಿರುವಿರಾ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>ನೀವು ಎಂದಾದರೂ ಆಸ್ಪತ್ರೆಯ ಶಸ್ತ್ರಚಿಕಿತ್ಸೆ ಸ್ವೀಕರಿಸಿದ್ದೀರಾ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>ಆ ಸಮಯದಲ್ಲಿ ರೋಗದ ಹೆಸರು ಏನು?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>ನೀವು ಪ್ರಸ್ತುತ ಕುಡಿಯುತ್ತಿರುವ ಯಾವುದೇ ಔಷಧವಿದೆಯೇ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<h2>COPD</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>ನೀವು ದಿನಕ್ಕೆ ಎಷ್ಟು ಸಿಗರೇಟುಗಳನ್ನು ಧೂಮಪಾನ ಮಾಡುತ್ತೀರಿ? <br/> (ಪುಸ್ತಕ / ದಿನ)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>ಬೆಳಿಗ್ಗೆ ಎದ್ದ ನಂತರ ನೀವು ಸಿಕ್ಕಿಹಾಕಿಕೊಳ್ಳುತ್ತೀರಾ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>ನೀವು ಸಾಮಾನ್ಯವಾಗಿ wheezy (ಝೆಜಿ, ಹಗ್ ಹಗ್) ಹೊಂದಿದ್ದೀರಾ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<h2>ಉಸಿರಾಟದ ಔಷಧ</h2>
			<div class="input-radio">
				<p>ಕಳೆದ 3 ತಿಂಗಳುಗಳಲ್ಲಿ ನೀವು ಕಂಡಿದ್ದೀರಾ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>ಕಳೆದ ಮೂರು ತಿಂಗಳುಗಳಲ್ಲಿ ಉಸಿರಾಟದ ಸಮಯದಲ್ಲಿ ನಿದ್ರೆ ಉಂಟಾಗಬಹುದು ಎಂದು ನಿಮ್ಮ ಕುಟುಂಬದವರು ನಿಮಗೆ ತಿಳಿಸಿದ್ದಾರೆ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>ನಿದ್ದೆ ಮತ್ತು ಮಲಗುವಿಕೆ ಅಥವಾ ಹಗಲಿನ ಸಮಯದಲ್ಲಿ ಅಥವಾ ಕೊನೆಯ 3 ತಿಂಗಳಲ್ಲಿ ನಿದ್ದೆ ಮಾಡುತ್ತಿರುವಿರಾ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<h2>ಮಧುಮೇಹ</h2>
			<div class="input-radio">
				<p>ಬಾಯಾರಿಕೆಗೆ ಸುಲಭವಾಗಿದೆಯೇ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>ನಿಮ್ಮ ರಕ್ತದ ಸಕ್ಕರೆಯ ಮಟ್ಟವು ಹೆಚ್ಚಿದೆಯೆಂದು ನಿಮಗೆ ಹೇಳಲಾಗಿದೆಯೆ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>ನಿಮ್ಮ ಕುಟುಂಬಕ್ಕೆ ಮಧುಮೇಹ ಇದೆಯೇ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">ಹೌದು</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">ಇಲ್ಲ</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('ನಾವು ತನಿಖಾ ವಿಷಯಗಳನ್ನು ಸರಿಪಡಿಸಿದ್ದೇವೆ.');">ತಿದ್ದುಪಡಿ</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>