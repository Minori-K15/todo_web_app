package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.HashGenerator;

@WebServlet("/account")
public class AccountCreateController extends HttpServlet {
	private static final String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";	
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
			// メッセージがからの場合
			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "アカウントを作成してください");
			}
			
			// accountへフォワード
			String view = "/WEB-INF/views/account.jsp";
			request.getRequestDispatcher(view).forward(request, response);
	}
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, 
		IOException {
		
			// idを取得して、id毎に閲覧するようにする
//			String user_id = request.getParameter("user_id");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String profile = request.getParameter("profile");
			
		// DB接続
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD)){			
			String hashedPassword = HashGenerator.generateHash(password);
			String sql = "INSERT INTO users (username, `password`, profile) VALUES (?, ?, ?)";
			
			try (PreparedStatement statement = connection.prepareStatement(sql)){
				
				// ?に対して
				statement.setString(1, username);
				statement.setString(2, hashedPassword);
				statement.setString(3, profile);
				statement.executeUpdate();
				
				request.setAttribute("message", username + " のアカウントを作成できました" + "<br>" + "ログインしてください");
				
				// login.jspへフォワード
				String view = "/WEB-INF/views/login.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response);
				
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
