<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Todo詳細</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
  <script src="./js/script.js"></script>
</head>
<body>
  <h1><strong>Todo詳細</strong></h1>
    <% String message = (String)request.getAttribute("message"); %>
    <p><%= message %></p>
    <p><strong>作成日：</strong><%= request.getAttribute("days") %></p>
    <p><strong>期限日：</strong><%= request.getAttribute("limit") %></p>
    <p><strong>優先度：</strong><%= request.getAttribute("priority") %></p>
    <p><strong>タイトル：</strong><%= request.getAttribute("title") %></p>
    <p><strong>本文：</strong><%= request.getAttribute("content") %></p><br>
    
    <div class="d-grid gap-2 d-md-block">
      <button class="btn btn-light"><a href="list">戻る</a></button>
      <button class="btn btn-light"><a href='edit?id=<%= request.getAttribute("id") %>'>編集</a></button>
      <button class="btn btn-light"><a href='destroy?id=<%= request.getAttribute("id") %>'>削除</a></button>
    </div>
</body>
</html>
