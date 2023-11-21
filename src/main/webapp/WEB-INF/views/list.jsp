<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Todoリスト</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
  <script src="./js/script.js"></script>
</head>
<body>
  <h1><strong>Todoリスト</strong></h1>
   <% String username = (String)request.getAttribute("username"); %>
    <% String message = (String)request.getAttribute("message"); %>
    <p>ようこそ <%= username %> さん <br> <%= message %></p>
<%--     <p><%= message %></p> --%>
 
  <form action="sort" method="get">
  <select id="select" name="select">
  <% String select = (String)request.getAttribute("select"); %>
  <option value="days_asce">作成日(昇順)</option>
  <option value="days_desc">作成日(降順)</option>
  <option value="limit_asce">期限日(昇順)</option>
  <option value="limit_desc">期限日(降順)</option>
  <option value="priority_asce">優先度(昇順)</option>
  <option value="priority_desc">優先度(降順)</option>
  </select>
  <button class="btn btn-light" type="submit">並び替え</button>
  </form>
  
  <table class="table table-striped table-hover table-sm">
  <thead  align="center" class="table-primary">
  <th width="30"><strong>ID</strong></th>
  <th width="100"><strong>作成日</strong></th>
  <th width="100"><strong>期限日</strong></th>
  <th width="100"><strong>優先度</strong></th>
  <th width="100"><strong>タイトル</strong></th><br>
  </thead>
  <tbody>
  <% ArrayList<HashMap<String, String>> rows =
  (ArrayList<HashMap<String, String>>)request.getAttribute("rows");
  %>
  
  <% for (HashMap<String, String> columns : rows) { %>
  <tr>
  <td align="center" class="table-light"><%= columns.get("id") %></td>
  <td align="center" class="table-light"><%= columns.get("days") %></td>
  <td align="center" class="table-light"><%= columns.get("limit") %></td>
  <td align="center" class="table-light"><%= columns.get("priority") %></td>
  <td align="center" class="table-light"><button class="btn btn-light"><a href='show?id=<%= columns.get("id") %>'><%= columns.get("title") %></a></button></td>
  </tr>
  <% } %>
  </tbody>
  </table>
  <div class="d-grid gap-2 d-md-block">
  <button class="btn btn-light" type="button"><a href="new">新規作成</a></button>
  <button class="btn btn-light" type="button"><a href="all_delete">一括削除</a></button>
<!--   <button class="btn btn-light" type="button"><a href="login">戻る</a></button> -->
  <button class="btn btn-light" type="button"><a href="session">ログアウト</a></button>
  </div>
  </body>
</html>