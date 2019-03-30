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
			<h2>初回健康チェック</h2>
			<div class="input-radio">
				<p>アレルギーはありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q1" value="" id="q1_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q1" value="" id="q1_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
            <div class="input-radio">
                <p>たばこを吸いますか？</p>
                <ul class="regulation-switch adjustment-bottom-margin flex-between">
                    <li><input type="radio" name="q10" value="" id="q10_check1" onclick="updateControls()"><label for="yes">はい</label></li>
                    <li><input type="radio" name="q10" value="" id="q10_check2" checked onclick="updateControls()"><label for="no">いいえ</label></li>
                </ul>
            </div>
			<div class="input-radio">
				<p>食欲の異常はありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q2" value="" id="q2_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q2" value="" id="q2_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<h2>内科</h2>
			<div class="input-radio">
				<p>自覚症状はありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q3" value="" id="q3_check1" onclick="updateControls()"><label for="yes">はい</label></li>
					<li><input type="radio" name="q3" value="" id="q3_check2" checked onclick="updateControls()"><label for="no">いいえ</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>自覚症状はいつ頃からありますか？</label><textarea rows="1" cols="" id="q4"></textarea>
			</p>
			<p class="input-vertical adjustment-bottom-margin">
				<label>どのような症状がありますか？</label><textarea rows="1" cols="" id="q5"></textarea>
			</p>
			<div class="input-radio">
				<p> 最近診察を受けましたか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q6" value="" id="q6_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q6" value="" id="q6_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>今までに入院手術を受けたことがありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q7" value="" id="q7_check1" onclick="updateControls()"><label for="yes">はい</label></li>
					<li><input type="radio" name="q7" value="" id="q7_check2" checked onclick="updateControls()"><label for="no">いいえ</label></li>
				</ul>
			</div>
			<p class="input-vertical adjustment-bottom-margin">
				<label>その時の病名は何ですか？</label><textarea rows="1" cols="" id="q8"></textarea>
			</p>
			<div class="input-radio">
				<p>現在飲んでいる薬はありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q9" value="" id="q9_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q9" value="" id="q9_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<h2>COPD</h2>
			<p class="input-vertical adjustment-bottom-margin"><label>1日に何本くらい、タバコを吸いますか？<br/>(本/日)</label><input type="number" pattern="\d*" id="q11"></p>
			<div class="input-radio">
				<p>朝起きてすぐにたんがからむことがよくありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q12" value="" id="q12_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q12" value="" id="q12_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>喘鳴(ゼイゼイ，ヒューヒュー)がよくありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q13" value="" id="q13_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q13" value="" id="q13_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<h2>呼吸器内科</h2>
			<div class="input-radio">
				<p>この3ヶ月間にいびきをかきましたか。</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q14" value="" id="q14_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q14" value="" id="q14_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>この3ヶ月間に、睡眠中に呼吸が止まることがあると家族から言われたことがありましたか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q15" value="" id="q15_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q15" value="" id="q15_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>この3ヶ月間に、昼間または起きているべき時間帯に、眠気が強くて眠ってしまいそうになったり居眠りしたことがありましたか。</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q16" value="" id="q16_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q16" value="" id="q16_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<h2>糖尿病</h2>
			<div class="input-radio">
				<p>のどが渇きやすいですか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q17" value="" id="q17_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q17" value="" id="q17_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>血糖値が高いと言われたことはありますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q18" value="" id="q18_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q18" value="" id="q18_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<div class="input-radio">
				<p>家族に糖尿病の方はいますか？</p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="q19" value="" id="q19_check1"><label for="yes">はい</label></li>
					<li><input type="radio" name="q19" value="" id="q19_check2" checked><label for="no">いいえ</label></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:48px"><a href="javascript:void(0)" onclick="alert('問診内容を修正しました。');">修正</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>