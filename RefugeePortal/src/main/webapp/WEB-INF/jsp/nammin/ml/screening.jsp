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
			<h2>പ്രാഥമിക ആരോഗ്യ പരിശോധന</h2>
			<div class="input-radio">
				<p>നിങ്ങൾക്ക് എന്തെങ്കിലും അലർജി ഉണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>നിങ്ങൾ പുകവലിക്കുമോ?</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">അതെ</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">ഇല്ല</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>അസാധാരണമായ വിശപ്പ് ഉണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<h2>ആന്തരിക മരുന്ന്</h2>
			<div class="input-radio">
				<p>നിങ്ങൾക്ക് ആത്മനിഷ്ഠമായ ലക്ഷണങ്ങൾ ഉണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>നിങ്ങൾക്ക് ആത്മവിശ്വാസം ഉള്ളത് എപ്പോഴാണ്?</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>രോഗലക്ഷണങ്ങൾ എന്തൊക്കെയാണ്?</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> അടുത്തിടെ നിങ്ങൾ ആലോചിച്ചിട്ടുണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>നിങ്ങൾക്ക് എപ്പോഴെങ്കിലും ആശുപത്രിയിലത്തെൽ ശസ്ത്രക്രിയ ലഭിച്ചിട്ടുണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>ആ സമയത്ത് അസുഖത്തിന്റെ പേര് എന്താണ്?</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>നിങ്ങൾ നിലവിൽ കുടിക്കുന്ന മരുന്ന് ഉണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<h2>സി.ഒ.പി.ഡി</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>ഒരു ദിവസത്തിൽ എത്ര സിഗരറ്റ് വലിക്കും? <br/> (പുസ്തകം / ദിവസം)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>രാവിലെ എഴുന്നേൽക്കുമ്പോഴാണ് പലപ്പോഴും നീണ്ടുകിടക്കുന്നത്?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>നിനക്ക് പലപ്പോഴും സൂചി ഉണ്ടോ (സെസി, ഹ്യൂ ഹ്യൂ)?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<h2>ശ്വാസകോശ ചികിത്സ</h2>
			<div class="input-radio">
				<p>കഴിഞ്ഞ 3 മാസത്തിൽ നിങ്ങൾ ഹൃദയമിടിപ്പിച്ചോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>കഴിഞ്ഞ മൂന്നുമാസത്തിനുള്ളിൽ ശ്വസനം തടയാൻ സാധിക്കുമെന്ന് നിങ്ങളുടെ കുടുംബാംഗങ്ങൾ അറിയിച്ചിട്ടുണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>ഉറക്കവും ഉറക്കവും അല്ലെങ്കിൽ കഴിഞ്ഞ 3 മാസത്തിലോ അല്ലെങ്കിൽ കഴിഞ്ഞ 3 മാസത്തിലോ നിങ്ങൾ ഉറങ്ങിയോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<h2>പ്രമേഹം</h2>
			<div class="input-radio">
				<p>ദാഹിക്കാൻ എളുപ്പമാണോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>നിങ്ങളുടെ രക്തത്തിലെ പഞ്ചസാരയുടെ അളവ് ഉയർന്നതാണെന്ന് നിങ്ങൾക്ക് പറയാൻ കഴിയുമോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>നിങ്ങളുടെ കുടുംബത്തിന് പ്രമേഹം ഉണ്ടോ?</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">അതെ</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">ഇല്ല</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('ഞങ്ങൾ അന്വേഷണ ഉള്ളടക്കം തിരുത്തി.');">തിരുത്തൽ</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>