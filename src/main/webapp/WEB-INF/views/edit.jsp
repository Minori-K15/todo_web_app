<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Todo編集</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
  <script src="./js/script.js"></script>
</head>
<body>
    <h1>Todo編集</h1>
      <% String message = (String)request.getAttribute("message"); %>
     <p><%= message %></p>
     
    <form action="update" method="get">
      <input type="hidden" name="id" value='<%= request.getAttribute("id")%>'>
      <label for="username">作成者</label><br>
      <input type="text" name="username" value='<%= request.getAttribute("username")%>' readonly><br>
      <label for="days">作成日</label><br>
      <input type="text" name="days" value='<%= request.getAttribute("days")%>'><br>
      <label for="limit">期限</label><br>
      <input type="text" name="limit" value='<%= request.getAttribute("limit")%>'><br>

      <% 
       String priority = (String)request.getAttribute ("priority"); 
       String selected_high = (priority != null && priority.equals("high") ? "selected" : ""); 
       String selected_normal = (priority != null && priority.equals("normal") ? "selected" : "");
       String selected_low = (priority != null && priority.equals("low") ? "selected" : "");
       %>
<%--
      if(priority.equals("high")){
        String selected_high = "selected";
      } else if (priority.equals("normal")) {
        String selected_normal = "selected";
      } else if(priority.equals("low")) {
        String selected_low = "selected";
      }
      %> --%>
      <label for="priority">優先度</label><br>
      <select name = "priority">
      <option value="high" <%= selected_high %>>high</option>
      <option value="normal"<%= selected_normal %>>normal</option>
      <option value="low"<%= selected_low %>>low</option>
      </select><br>
      <label for="title">タイトル</label><br>
      <input type="text" name="title" value='<%= request.getAttribute("title")%>'><br>
      <br>
      <label for="content">本文</label><br>
      <textarea name="content" id="" cols="30" rows="8"><%= request.getAttribute("content")%></textarea><br>
    <div class="d-grid gap-2 d-md-block">
      <button class="btn btn-light" type="submit">保存する</button>
      <button class="btn btn-light"><a href='show?id=<%= request.getAttribute("id")%>'>キャンセル</a></button>
    </div>
  </form>
</body>
</html>
