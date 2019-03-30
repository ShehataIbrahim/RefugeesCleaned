<%@page import="net.hitachifbbot.servlet.CategoryListServlet.Category" %>
<%@page import="java.util.List" %>
<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
</head>
<body>
<h1>カテゴリー一覧</h1>
    <hr>
    <% List<Category> list = (List<Category>)request.getAttribute("category");%>
    <% if(list.size() == 0){%>
        <h3> カテゴリーが存在しません。 </h3>
    <% }else{%>
        <% for(Category c : list){%>
        <a href="./screening_list?c_id=<%= c.getCategoryId()%>&c_name=<%= c.getCategoryName()%>"><%= c.getCategoryName()%></a>
        <% } %>
    <% } %>
    <hr>
hitachi-fbbot.net
</body>
</html>