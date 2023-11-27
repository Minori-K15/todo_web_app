package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/create")
public class CreateController extends HttpServlet {
	
	private static String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String SQL = "INSERT INTO posts (user_id, days, `limit`, priority, title, content) VALUES (?, ?, ?, ?, ?, ?)";
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		
		// セッション接続
		int user_id = session(request, response);
		
		// JDBCドライバー接続
		jdbc_Connection();
		
		// パラメータを取得
		String days = request.getParameter("days");
		String limit = request.getParameter("limit");
		String priority = priority(request.getParameter("priority"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(SQL)){
				
				// 一番初めの?に対して
				statement.setInt(1, user_id);
				statement.setString(2, days);
				statement.setString(3, limit);
				statement.setString(4, priority);
				statement.setString(5, title);
				statement.setString(6, content);
				statement.executeUpdate();
				request.setAttribute("message","タイトル:" + title + "の新規作成ができました");
				
		} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		String forward = "/list";
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
		dispatcher.forward(request, response);
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
			
			request.setAttribute("username", username);
			
			// メッセージの表示
			if (request.getAttribute("message") == null) {
				// nullの場合のメッセージ
				request.setAttribute("message", "todoを管理しましょう");
			}
		}
		return id;
	}
	
	// JDBCドライバー接続
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
		case "high":
			priority = "0";
			break;
		case "normal":
			priority = "1";
			break;
		case "low":
			priority = "2";
			break;
	}
		return priority;
	}
}