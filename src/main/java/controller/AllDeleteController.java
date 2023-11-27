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
 * 全て削除
 */

@WebServlet("/all_delete")
public class AllDeleteController extends HttpServlet {
	private static final String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";	
	private static final String SQL_DELETE = "DELETE FROM posts WHERE id = ?";	
	private static final String SQL_CLEAR = "ALTER TABLE todo.posts auto_increment = 1";	
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		
		// セッション接続
		int id = session(request, response);
		
		// JDBCドライバ接続
		jdbc_Connection();
		
		// DB接続
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD))
		{
			// テーブル内のデータを全て削除
			PreparedStatement statement_delete = connection.prepareStatement(SQL_DELETE);
			statement_delete.setInt(1, id);
			statement_delete.executeUpdate();
			
			// IDの連番をclear
			PreparedStatement statement_cleara = connection.prepareStatement(SQL_CLEAR);
			statement_cleara.executeUpdate();
			
			// 正常に処理が完了した時のメッセージ
			request.setAttribute("message","全て削除しました");
				
		} catch (Exception e) {
			
			// 例外発生時のメッセージ
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		
		// listにフォワード
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
