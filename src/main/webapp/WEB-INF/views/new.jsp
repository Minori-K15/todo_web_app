<%@ page language="java"
contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Todo新規作成</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
  <script src="./js/script.js"></script>
</head>
<body>
    <h1><strong>Todo新規作成</strong></h1>
    <% String username = (String)request.getAttribute("username"); %>
    <% String user_id = (String)request.getAttribute("user_id"); %>
    <p><%= username %> さん</p>
    <% String message = (String)request.getAttribute("message"); %>
   <p><%= message %></p>
    <form action="create" method="get">
      <input type="hidden" name="user_id" value='<%= request.getAttribute("user_id")%>'>
      <label for="days">作成日</label><br>
      <input type="text" name="days" value='<%= request.getAttribute("days")%>'><br>
      <label for="limit">期限日</label><br>
      <input type="text" name="limit" value='<%= request.getAttribute("limit")%>'><br>
      <label for="priority">優先度</label><br>
      <select name ="priority" width = "147" height = "21.5">
      <option value="high">high</option>
      <option value="normal">normal</option>
      <option value="low">low</option>
      </select>
     <!--  <input type="text" name="priority" value=''> -->
      <br>
      <label for="title">タイトル</label><br>
      <input type="text" name="title" value=''><br>
      <br>
      <label for="content">本文</label><br>
      <textarea name="content" id="" cols="30" rows="10"></textarea><br>
      <div class="d-grid gap-2 d-md-block">
      <button class="btn btn-light" type="submit">保存</button>
      <button class="btn btn-light"><a href='list'>キャンセル</a></button>
      <button class="btn btn-light"><a href='list'>戻る</a></button>
      </div>
  </form>
</body>
</html>
