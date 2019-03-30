<!DOCTYPE html>
<%@page import="com.refugees.common.Validator"%>
<%@page import="com.refugees.servlets.doctor.ScreeningDetailServlet.ScreeningDetail"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<html lang="en" xmlns:th="http://www.thymeleaf.org" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, viewport-fit=cover" />
<meta name="format-detection" content="telephone=no" />
<meta name="robots" content="noindex, nofollow" />
<title>Migrants and Refugee Suport Portal</title>
<link href="/css/html5-doctor-reset-stylesheet.min.css" rel="stylesheet" />
<link href="/css/pc-style-basic.css" rel="stylesheet" />
</head>
<%
	Locale locale = request.getLocale();
	ResourceBundle msgBundle = ResourceBundle.getBundle("Messages", locale);
	%>
<body>
<div id="wrapper">

	<header>
		<div>
			<h1><a href="index.html"><%=msgBundle.getString("common.title")%></a></h1>
		</div>
	</header>
<div id="google_translate_element"></div>
	<section id="static-list-area">
		<h2><span>Name:</span>${nammin_name}</h2>		
		<div class="list-area">
        <table class="interviewee-data">
            <colgroup>
                <col width="68%" />
                <col width="32%" />
            </colgroup>
            <tr>
                <th>Question content</th>
                <th>Reply</th>
            </tr>
			<%
			@SuppressWarnings("unchecked")
			List<ScreeningDetail> details = (List<ScreeningDetail>) request
						.getAttribute("details");
				for (ScreeningDetail d : details) {
			%>
				<tr >
					<td id="Question<%=d.getQuestionId()%>"><%=msgBundle.getString(d.getQuestion()) %></td>
					<td><%=Validator.evaluateQuestionValue(d.getQuestionId(), d.getAnswer()) %></td>
				</tr>
				<%} %>
        </table>
    </div>
    <p class="btn-large" style="width:30%;margin:32px auto 0;float: right;"><a href="javascript:showSendMessage()" onclick="">send a message</a></p>
    <p class="btn-large" style="width:30%;margin:32px auto 0;float: right;margin-right: 5%;background: #f3f3f3b0;"><a href="javascript: void(0)" style="background-color: #f3f3f3b0!important;color: rgba(31,144,228,1);font-weight: normal;" onclick="">Confirm This</a></p>
     <p class="btn-large" style="width:30%;margin:32px auto 0;float: left;"><a href="/doctor/index.html" onclick="">Back</a></p>
</section>
</div>
<div id="message_area" style="display: none;">
	<input type="text" id="message-content" class="input-message"></input>
	
	<br/>
	<p class="btn-large" style="width:20%;margin: 23px auto 0;float: right;margin-right: 18%;">
		<a href="javascript:sendMessage()" onclick="" style="text-align: center;">Send</a>
	</p>
</div>
<form action="/doctor/logout" method="POST" name="form_logout">
	<input type="hidden" th:name="${csrfTokenName}" th:value="${csrfToken2}" />
</form>
</body>
<script src="/js/jquery-3.3.1.min.js"></script>
<script>
    function confirmLogout(){
        if(!confirm("Do you want to log out?")){
            return false;
        }else{
            form_logout.submit();
        }
    }
</script>
<script type="text/javascript">
function confirm()
{
	$.ajax({
		type : "POST",
		url : 'doctor/screening_detail?action=CONFIRM&id=${namminID}',
		
		success : function(data) {
			alert(data.message);
		}
	});
}
</script>
<script type="text/javascript">
function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
}
function showSendMessage()
{
	$('#message_area').show();
}
function sendMessage()
{
	var receiverName='${nammin_name}';
	$.ajax({
		type : "GET",
		url : "https://refugees.i4.io/FbChatbot/Meessanger?username="+receiverName+"&message="+$('#message-content').val(),
		success : function(data) {
			$('#message_area').hide();
			$('#message-content').val(''),
			alert('message sent');
			$('#message_area').hide();
		}
	});
}
</script>

<script type="text/javascript" src="http://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
</html>
