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
			<h2>ערשטע געזונט קאָנטראָל</h2>
			<div class="input-radio">
				<p>צי איר האָבן קיין אַלערדזשיז?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>צי איר רויך?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">Yes</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">ניין</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>זענען דאָרט אַבנאָרמאַל אַפּעטיט?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<h2>אינערלעכער מעדיסינע</h2>
			<div class="input-radio">
				<p>צי איר האָבן סאַבדזשעקטיוו סימפּטאָמס?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">ניין</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>ווען איר האָבן סאַבדזשעקטיוו סימפּטאָמס?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>וואָס זענען די סימפּטאָמס?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> האָבן איר לעצטנס געזאָגט?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>האָבן איר אלץ באקומען שפּיטאָליזאַטיאָן כירורגיע?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">ניין</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>וואָס איז דער נאָמען פון די קרענק אין אַז צייַט?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>איז עס קיין מעדיצין וואָס איר זענט דערווייַל געטרונקען?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<h2>COPD</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>ווי פילע סיגאַרעטטעס טאָן איר רייכערן אַ טאָג? <br/> (ספר / טאָג)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>צי איר אָפט באַקומען סטאַק אַרויף נאָך געטינג אַרויף אין דער מאָרגן?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>צי איר אָפט האָבן וויזיז (Zezei, יו יו)?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<h2>רעספּעראַטאָרי מעדיצין</h2>
			<div class="input-radio">
				<p>האָט איר סנאָרעד אין די לעצטע 3 חדשים?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>האָבן איר שוין געזאָגט דורך דיין משפּחה אַז ברידינג זאל האַלטן אין שלאָפן אין דער פאַרגאַנגענהייַט 3 חדשים?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>צי האָט איר פילן פאַרשלאָפן און סליפּינג אָדער פאַלינג שלאָפנדיק אין די דייטיים אָדער אין די לעצטע 3 חדשים?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<h2>צוקערקרענק</h2>
			<div class="input-radio">
				<p>איז עס גרינג צו דאָרשטיק?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>האָבן איר שוין געזאָגט אַז דיין בלוט צוקער איז הויך?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>טוט דיין משפּחה האָבן צוקערקרענק?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">Yes</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">ניין</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('מיר קערעקטאַד די אָנפרעג אינהאַלט.');">קאָררעקטיאָן</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>