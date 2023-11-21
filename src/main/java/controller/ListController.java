package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 * Todoリスト一覧
 */

@WebServlet("/list")
public class ListController extends HttpServlet {
	private static final String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String SQL_USERS_ID = "SELECT id FROM todo.users WHERE username = ? ";
//	private static final String SQL_POSTS = "SELECT * FROM posts WHERE user_id = ? ";
	private static final String SQL = "SELECT * FROM posts";
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
			// メッセージの表示
			if (request.getAttribute("message") == null) {
				// nullの場合のメッセージ
				request.setAttribute("message", "todoを管理しましょう");
			}
			
			// センションを取得
			HttpSession session = request.getSession(true);
			String username = (String) session.getAttribute("username");
			
			// セッションから取得したusernameでログイン状態のチェックを行う
			if(username != null ) {
			request.setAttribute("username", username);
			} else {
				// 未ログインの場合、ログイン画面に遷移
				response.sendRedirect("login");
			}
			
			// JDBCドライバをロード
			try {
				Class.forName(JDBC_DRIVER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		// DBログイン
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(SQL);
			ResultSet results = statement.executeQuery()){
				// 取得したデータをリスト化
				ArrayList<HashMap<String, String>> rows = new
				ArrayList<HashMap<String, String>> ();
				
				while (results.next()) {
					HashMap<String, String> conlums = new
					HashMap<String, String> ();
					
					String id = results.getString("id");
					conlums.put("id", id);
					
					String days = results.getString("days");
					conlums.put("days", days);

					String limit = results.getString("limit");
					conlums.put("limit", limit);
					
					String priority = results.getString("priority");
					switch (priority) {
					case "0":
						priority = "high";
						break;
					case "1":
						priority = "normal";
						break;
					case "2":
						priority = "low";
						break;
					}
					conlums.put("priority", priority);
					
					String title = results.getString("title");
					conlums.put("title", title);
					
					String content = results.getString("content");
					conlums.put("content", content);
					
					rows.add(conlums);
				}
				request.setAttribute("rows", rows);
		} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
	
		// listリダイレクト
		String view = "/WEB-INF/views/list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	// user_idを取得
//	private static String userId(String users ) {
//		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
//			PreparedStatement statement = connection.prepareStatement(SQL_USERS_ID)){
//				
//				statement.setString(1, users);
//				statement.executeQuery();
//				
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return users ;
//	}
}
