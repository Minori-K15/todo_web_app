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
import jakarta.servlet.http.HttpSession;
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
			
			// センションを取得
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			request.setAttribute("username", username);
			
			// 
			String view = "/WEB-INF/views/account.jsp";
			request.getRequestDispatcher(view).forward(request, response);
	}
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, 
		IOException {
			// idを取得して、id毎に閲覧するようにする
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String profile = request.getParameter("profile");
		
			try {
				Class.forName(JDBC_DRIVER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
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
}
