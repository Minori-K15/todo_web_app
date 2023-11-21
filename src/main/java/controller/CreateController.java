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

@WebServlet("/create")
public class CreateController extends HttpServlet {
	private static final String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
			
			// センションを取得
//			HttpSession session = request.getSession();
//			String username = (String) session.getAttribute("username");
//			request.setAttribute("username",username);
			
			// セッションから取得したusernameでログイン状態のチェックを行う
//			if (username != null) {
//				request.setAttribute("username", username);
//				String view = "/WEB-INF/views/new.jsp";
//				request.getRequestDispatcher(view).forward(request, response);
//				} else {
//					// 未ログインの場合、ログイン画面に遷移
//					response.sendRedirect("login");
//				}
			
			try {
				Class.forName(JDBC_DRIVER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			// idを取得して、id毎に閲覧するようにする
			String days = request.getParameter("days");
			String limit = request.getParameter("limit");
			String priority = request.getParameter("priority");
			// 置換
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
			String title = request.getParameter("title");
			String content = request.getParameter("content");
		
		String sql = "INSERT INTO posts (days, `limit`, priority, title, content) VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(sql)){
				
				// 一番初めの?に対して
				statement.setString(1, days);
				statement.setString(2, limit);
				statement.setString(3, priority);
				statement.setString(4, title);
				statement.setString(5, content);
				statement.executeUpdate();
				request.setAttribute("message","タイトル:" + title + "の新規作成ができました");
				
		} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		String forward = "/list";
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
		dispatcher.forward(request, response);
	}
}