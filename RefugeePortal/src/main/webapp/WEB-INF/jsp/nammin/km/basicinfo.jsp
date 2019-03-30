<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<%@ page import="net.hitachifbbot.filter.CSRFFilter" %>
<!DOCTYPE html>
<html lang="ja">
<head>
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
  name : datas["1"],
  gender : datas["2"],
  tel : datas["4"],
  from : datas["5"],
  job : datas["6"],
  country : datas["7"],
  address : datas["8"],
  supporter : datas["9"],
  family_count : datas["10"],
  zairyu_card_num : datas["11"],
  zairyu_sikaku : datas["13"],
}

addYMD("birthday", datas["3"]);
addYMD("zairyu_card_limit", datas["12"]);
addYMD("zairyu_manryobi", datas["14"]);

function addYMD(key, val){
  controlVal[key + "1"] = val.substr(0,4);
  controlVal[key + "2"] = val.substr(4,2);
  controlVal[key + "3"] = val.substr(6,2);
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
}

window.onload = setup;

</script>
</head>
<body>
<div id="wrapper">
	<div class="header" id="header-bg-basic-info">
		<p>Personal</p>
		<p><a href="javascript:alert('កំណត់ត្រាវេជ្ជសាស្រ្តអេឡិចត្រូនិចនឹងត្រូវបានបង្ហាញ។')">កំណត់ត្រាវេជ្ជសាស្ត្រអេឡិចត្រូនិ</a></p>
	</div>

	<div id="basic-informations">
		<form action="" method="post" id="">
			<p class="input-vertical"><label>ឈ្មោះ</label><input type="text" id="name"></p>
			<div class="input-radio">
				<p>ការរួមភេទ</p>
				<ul class="regulation-switch flex-between">
					<li><input type="radio" name="regulation-01" value="" id="gender_check1" checked><label for="man">ប្រុស</label></li>
					<li><input type="radio" name="regulation-01" value="" id="gender_check2"><label for="woman">ស្រី</label></li>
				</ul>
			</div>
			<div class="input-horizon">
				<p>កាលបរិច្ឆេទកំណើត</p>
				<ul>
					<li><input type="number" pattern="\d*" id="birthday1"></li>
					<li> / </li>
					<li><input type="number" pattern="\d*" id="birthday2"></li>
					<li> / </li>
					<li><input type="number" pattern="\d*" id="birthday3"></li>
				</ul>
			</div>
			<p class="input-vertical">
				<label>លេខទូរស័ព្ទ</label><input type="number" pattern="\d*" id="tel"></textarea>
			</p>
			<p class="input-vertical">
				<label>ប្រភពដើម</label><textarea rows="1" cols="" id="from"></textarea>
			</p>
			<p class="input-vertical">
				<label>អាជីព</label><textarea rows="1" cols="" id="job"></textarea>
			</p>
			<p class="input-vertical">
				<label>សញ្ជាតិ</label><textarea rows="1" cols="" id="country"></textarea>
			</p>
			<p class="input-vertical">
				<label>ទីកន្លែងរស់នៅ</label><textarea rows="1" cols="" id="address"></textarea>
			</p>
			<p class="input-vertical">
            				<label>ថែរក្សា</label><textarea rows="1" cols="" id="supporter"></textarea>
            </p>
			<p class="input-vertical"><label>ទំហំគ្រួសារ (មនុស្ស)</label><input type="number" pattern="\d*" id="family_count"></p>
			<p class="input-vertical"><label>ប័ណ្ណស្នាក់នៅ·លេខវិញ្ញាបនប័ត្រអ្នកស្នាក់នៅអចិន្ត្រៃយ៍ពិសេស</label><input type="number" pattern="\d*" id="zairyu_card_num"></p>
			<div class="input-horizon">
				<p>កាលបរិច្ឆេទផុតកំណត់</p>
				<ul>
					<li><input type="number" pattern="\d*" id="zairyu_card_limit1"></li>
					<li> / </li>
					<li><input type="number" pattern="\d*" id="zairyu_card_limit2"></li>
					<li> / </li>
					<li><input type="number" pattern="\d*" id="zairyu_card_limit3"></li>
				</ul>
			</div>
            <p class="input-vertical">
                <label>ស្ថានភាពនៃការស្នាក់នៅ</label><textarea rows="1" cols="" id="zairyu_sikaku"></textarea>
            </p>
			<div class="input-horizon">
				<p>កាលបរិច្ឆេទផុតកំណត់</p>
				<ul>
					<li><input type="number" pattern="\d*" id="zairyu_manryobi1"></li>
					<li> / </li>
					<li><input type="number" pattern="\d*" id="zairyu_manryobi2"></li>
					<li> / </li>
					<li><input type="number" pattern="\d*" id="zairyu_manryobi3"></li>
				</ul>
			</div>
			<p class="btn-large" style="margin-top:52px"><a href="javascript:void(0)" onclick="alert('ពត៌មានមូលដ្ឋានដែលមានកាលកំណត់។');">ការកែតម្រូវ</a></p>
		</form>
	</div>
</div>
<!-- SCRIPT -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
</body>
</html>