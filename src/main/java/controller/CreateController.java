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
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
			// メッセージがからの場合
			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "todoを管理しましょう");
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
			
			// SQLに接続するための情報
			String url = "jdbc:mysql://localhost/todo";
			String user = "root";
			String password = "";
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		String sql = "INSERT INTO posts (days, `limit`, priority, title, content) VALUES(?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection (url, user, password);
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