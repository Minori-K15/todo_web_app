package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private static final String SQL = "SELECT * FROM posts WHERE user_id = ? ";
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		
		// セッション
		int id = session(request, response);
		
		// DB接続
		jdbc_Connection();
		
		// DB接続
		try ( Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(SQL)){
				// 
				
				statement.setInt(1, id);
				ResultSet results = statement.executeQuery();
				
				// 取得したデータをリスト化
				ArrayList<HashMap<String, String>> rows = new
				ArrayList<HashMap<String, String>> ();
					
				while (results.next()) {
					HashMap<String, String> conlums = new
					HashMap<String, String> ();
						
					String user_id = results.getString("id");
					conlums.put("id", user_id);
						
					String days = results.getString("days");
					conlums.put("days", days);
					
					String limit = results.getString("limit");
					conlums.put("limit", limit);
						
					String priority = priority(results.getString("priority"));
					conlums.put("priority", priority);
						
					String title = results.getString("title");
					conlums.put("title", title);
						
					String content = results.getString("content");
					conlums.put("content", content);
						
					rows.add(conlums);
					}
				request.setAttribute("rows", rows);
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", "SQLException:" + e.getMessage());
			} 
		
		// listへフォワード
		String view = "/WEB-INF/views/list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
//		response.sendRedirect("list");
	}
	
	// セッション接続
	private int session(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 変数の初期化
		int id = 0;
		String username = null;
		
		// サーバーの保持するセッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null) {
			// 未ログインの場合、ログイン画面に遷移
			response.sendRedirect("login");
		} else {
			// セッションに保持されているユーザー名を取得
			id = (int) session.getAttribute("id");
			username = (String) session.getAttribute("username");
			
			request.setAttribute("id", id);
			request.setAttribute("username", username);
			
			// メッセージの表示
			if (request.getAttribute("message") == null) {
				// nullの場合のメッセージ
				request.setAttribute("message", "todoを管理しましょう");
			}
		}
		return id;
	}
	
	// JDBCドライバー接続メソッド
	private void jdbc_Connection() {
		try {
			// JDBCドライバ接続
			Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// 優先度を置換するメソッド
	protected String priority (String priority) {
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
		return priority;
	}
}
