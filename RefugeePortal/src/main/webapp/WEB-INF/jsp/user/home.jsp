<%@page import="com.refugees.db.service.ScreeningQuestion"%>
<%@page import="java.util.List"%>
<%@page import="com.refugees.db.model.Category"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<%@ page import="net.hitachifbbot.filter.CSRFFilter"%>
<!DOCTYPE html>
<html>
<!-- Bad Code to be enhanced -->
<%
	Locale locale = request.getLocale();
	ResourceBundle msgBundle = ResourceBundle.getBundle("Messages", locale);
	String errorMessageCode = request.getAttribute("errorMessage") == null
			? null
			: request.getAttribute("errorMessage").toString();
%>
<head>
<meta charset="utf-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, viewport-fit=cover">
<meta name="format-detection" content="telephone=no">
<meta name="robots" content="noindex, nofollow">
<title><%=msgBundle.getString("common.title")%></title>
<link href="/css/html5-doctor-reset-stylesheet.min.css" rel="stylesheet">
<link href="/css/mobile-style-basic.css" rel="stylesheet">
<body>
<div id="google_translate_element"></div>
	<div id="wrapper">
		<div class="header header-no-btn" id="header-bg-medical-inquiry-data">
			<h1><%=msgBundle.getString("screening.title")%></h1>
		</div>
	</div>
	<script type="text/javascript">
		var questionKeys = new Array();
	var answers=<%=request.getAttribute("answers")%>;
		
	</script>
	<div id="medical-inquiry-data">
		<form action="" method="post" id="">
			<%
				Map<Category, List<ScreeningQuestion>> list = (Map<Category, List<ScreeningQuestion>>) request
						.getAttribute("categories");
				for (Category c : list.keySet()) {
			%>
			<h2><%=msgBundle.getString(c.getCategoryName())%></h2>
			<%
				for (ScreeningQuestion q : list.get(c)) {
						if (q.isYesNo()) {
			%>
			<div class="input-radio">
				<p id="Question<%=q.getQuestionId()%>"><%=msgBundle.getString(q.getQuestionTextKey())%></p>
				<ul class="regulation-switch adjustment-bottom-margin flex-between">
					<li><input type="radio" name="Q<%=q.getQuestionId()%>"
						value="" id="Q<%=q.getQuestionId()%>_check1"><label
						for="yes"><%=msgBundle.getString("common.yes")%></label></li>
					<li><input type="radio" name="Q<%=q.getQuestionId()%>"
						value="" id="Q<%=q.getQuestionId()%>_check2" checked><label
						for="no"><%=msgBundle.getString("common.no")%></label></li>
				</ul>
			</div>
			<%
				} else if (q.isText()) {
			%>
			<p class="input-vertical adjustment-bottom-margin">
				<label id="Question<%=q.getQuestionId()%>" ><%=msgBundle.getString(q.getQuestionTextKey())%></label>
				<textarea rows="1" cols="" id="Q<%=q.getQuestionId()%>"></textarea>
			</p>
			<%
				} else if (q.isNumeric()) {
			%>
			<p class="input-vertical adjustment-bottom-margin">
				<label><%=msgBundle.getString(q.getQuestionTextKey())%></label><input
					type="number" pattern="\d*" id="Q<%=q.getQuestionId()%>">
			</p>
			<%
				}
			%>
			<script type="text/javascript">
				questionKeys.push("<%=q.getQuestionId()%>");
			</script>
			<%
				}
				}
			%>
			<p id="btn_submit" class="btn-large" style="margin-top: 48px;">
				<a href="javascript:submitMedicalSurvey();"><%=msgBundle.getString("common.submit")%></a>
			</p>
		</form>
	</div>
</body>
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
<script src="/js/portal-mobile.ui.js"></script>
<script type="text/javascript">
Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};
	function submitMedicalSurvey() {
		//var selectionsMap={};
		for (var i = 0; i < questionKeys.length; i++) {
			var qKey = "Q" + questionKeys[i];
			var value;
			if ($('#' + qKey + '_check1').length > 0) {
				if ($('#' + qKey + '_check1')[0].checked)
					value = 1;
				else
					value = 0;
				$('#' + qKey + '_check1')[0].disabled=true;
				$('#' + qKey + '_check2')[0].disabled=true;
			} else {
				value = $('#' + qKey)[0].value;
				$('#' + qKey)[0].disabled=true;
			}
			answers[questionKeys[i]] = value;
			//$('#' + qKey)[0].disabled=true;
		}
		//console.log(JSON.stringify(selectionsMap));
		$.ajax({
			type : "POST",
			url : "/user/authenticated/home",
			data : answers, // serializes the form's elements.
			success : function(data) {
				alert(data.message);
				$('#btn_submit').remove();
			}
		});
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		if(Object.size(answers)>0)
			{
			$('#btn_submit').remove();
		for (var i = 0; i < questionKeys.length; i++) {
			var qKey = "Q" + questionKeys[i];
			var value = answers[questionKeys[i]];
			if ($('#' + qKey + '_check1').length > 0) {
				if (value == 1)
					$('#' + qKey + '_check1')[0].checked = true;
				else
					$('#' + qKey + '_check2')[0].checked = true;
				$('#' + qKey + '_check1')[0].disabled=true;
				$('#' + qKey + '_check2')[0].disabled=true;
			} else {
				if (value != null)
					$('#' + qKey)[0].value = value;
				$('#' + qKey)[0].disabled=true;
			}
		}
			}
	});
</script>
<script type="text/javascript">
function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
}
</script>

<script type="text/javascript" src="http://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
</html>