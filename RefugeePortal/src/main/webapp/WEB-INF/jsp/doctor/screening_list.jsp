<%@page import="net.hitachifbbot.servlet.DoctorScreeningListServlet.ScreeningResult" %>
<%@page import="java.util.List" %>
<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<
</head>
<body>
<h1>問診一覧</h1>
    <hr>

    <% List<ScreeningResult> list = (List<ScreeningResult>)request.getAttribute("screening_result");%>
    <% if(list.size() == 0){%>
        <h3> 問診一覧が存在しません </h3>
    <% }else{%>
        <% Integer categoryID = (Integer)request.getAttribute("category_id");%>
        <% String categoryName = (String)request.getAttribute("category_name");%>
        <h3> <%= categoryName%> </h3>
        <table>
            <tr>
                <th>問診日</th>
                <th>ユーザーID</th>
                <th>氏名</th>
                <th>結果</th>
            </tr>
        <% for(ScreeningResult r : list){%>
            <tr>
                <td><%= r.answeredAt%></td>
                <td><%= r.namminID%></td>
                <td>
                    <a href="./screening_detail?s_id=<%= r.screeningID%>&c_id=<%= categoryID%>&c_name=<%= categoryName%>&n_name=<%= r.namminName %>&n_id=<%= r.namminID %>">
                        <%= r.namminName%>
                    </a>
                </td>
                <td>
                    <a href="">
                        <%= r.result%>
                    </a>
                </td>
            </tr>
        <% } %>
        </table>
    <% }%>
    <form action="/doctor/logout" method="POST" onsubmit="return confirmLogout()">
    <input type="submit" value="ログアウト">
    <hr>
hitachi-fbbot.net
<script>
    function confirmLogout(){
        if(!confirm("ログアウトしますか？")){
            return false;
        }
    }
</script>
</body>
</html>
