package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/edit")
public class EditController extends HttpServlet {
	private static final String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";	
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
			// メッセージがからの場合
			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "todoを管理しましょう");
			}
			
			// センションを取得
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			request.setAttribute("username", username);
			
			// idを取得して、id毎に修正
			int postId = Integer.parseInt(request.getParameter("id"));
			
			try {
				Class.forName(JDBC_DRIVER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		String sql = "SELECT * FROM posts WHERE id = ?";
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(sql)){
				
				// 一番初めの?に対して
				statement.setInt(1, postId);
				ResultSet results = statement.executeQuery();
				
				while (results.next()) {
					
					String id = results.getString("id");
					request.setAttribute("id", id);
					
					String days = results.getString("days");
					request.setAttribute("days", days);

					String limit = results.getString("limit");
					request.setAttribute("limit", limit);
					
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
					request.setAttribute("priority", priority);
					
					String title = results.getString("title");
					request.setAttribute("title", title);
					
					String content = results.getString("content").replaceAll("¥n","<br>");
					request.setAttribute("content", content);
				}
		} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		String view = "/WEB-INF/views/edit.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	// セッションから取得したusernameでログイン状態のチェックを行う
//	if (username != null) {
//		request.setAttribute("username", username);
//		String view = "/WEB-INF/views/edit.jsp";
//		request.getRequestDispatcher(view).forward(request, response);
//		} else {
//			// 未ログインの場合、ログイン画面に遷移
//			response.sendRedirect("login");
//		}
}
