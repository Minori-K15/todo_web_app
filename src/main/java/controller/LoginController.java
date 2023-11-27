package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.HashGenerator;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 変数定義
	private static String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String SQL_SELECT = "SELECT * FROM users WHERE username = ? AND password = ?";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// メッセージ表示
		if (request.getAttribute("message") == null) {
			// nullの場合のメッセージ
			request.setAttribute("message", "ログインしてください");
		}
		
		// login.jspへフォワード
		String view = "/WEB-INF/views/login.jsp";
		request.getRequestDispatcher(view).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータ取得
		String username = request.getParameter("username");
		String password =request.getParameter("password");
		
		// JDBCドライバー接続
		jdbc_Connection();
		
		// DB接続
		try(Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
			String hashedPassword = HashGenerator.generateHash(password);
			
			try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT)){
				statement.setString(1, username);
				statement.setString(2, hashedPassword);
				ResultSet result = statement.executeQuery();
				
				if(result.next()) {
					int id = result.getInt("id");
					String user_id = result.getString("user_id");
					String profile = result.getString("profile");
					
					// サーバーの保持するセッションを取得する - セッションがなければ新規作成
					HttpSession session = request.getSession();
					
					// キーと値のペアでセッションに登録する
					session.setAttribute("id", id);
					session.setAttribute("username", username);
					session.setAttribute("profile", profile);
					
					// list.javaへリダイレクト
					response.sendRedirect("list");
				}	else {
					// 追加：3回以上間違えたら
					
						// 失敗時のメッセージ
						request.setAttribute("message", "ログイン失敗しました");
						
						// ログイン画面に戻る
						String view = "/WEB-INF/views/login.jsp";
						request.getRequestDispatcher(view).forward(request, response);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Database Connection Failed", e);
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new ServletException("Generate hash Failed", e);
		}
	}
	
	// JDBCドライバー接続
	protected void jdbc_Connection() throws ServletException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("jdbc driver failed.", e);
		}
	}
}
