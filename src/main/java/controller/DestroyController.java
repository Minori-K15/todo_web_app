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

/*
 * id毎に削除
 */

@WebServlet("/destroy")
public class DestroyController extends HttpServlet {
	private static final String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String SQL = "DELETE FROM posts WHERE id = ? AND user_id = ?";
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		
		// idを取得して、削除する
		int postId = Integer.parseInt(request.getParameter("id"));
		// セッション接続メソッド
		int user_id = session(request, response);
			
		// JDBCドライバー接続
		jdbc_Connection();
			
		// DB接続
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(SQL)){
				
				// 一番初めの?に対して
				statement.setInt(1, postId);
				statement.setInt(2, user_id);
				statement.executeUpdate();
				request.setAttribute("message","ID:" + postId + "の削除ができました");
				
		} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		
		// listへフォワード
		String forward = "/list";
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
		dispatcher.forward(request, response);
	}
	
	// セッション接続
	private int session(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 変数初期化
		int user_id = 0;
		
		// サーバーの保持するセッションを取得する
		HttpSession session = request.getSession(false);
		System.out.println(session);
		if (session == null) {
			// 未ログインの場合、ログイン画面に遷移
			response.sendRedirect("login");
		} else {
			// セッションに保持されているユーザー名を取得
			int id = (int) session.getAttribute("id");
//			String user_id = (String) session.getAttribute("user_id");
			String username = (String) session.getAttribute("username");
			
			// setパラメータ
			user_id = id;
			request.setAttribute("username", username);
			
			// メッセージの表示
			if (request.getAttribute("message") == null) {
				// nullの場合のメッセージ
				request.setAttribute("message", "todoを管理しましょう");
			}
		}
		return user_id;
	}
	
	// DB接続メソッド
	private void jdbc_Connection() {
		try {
			// JDBCドライバ接続
			Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
