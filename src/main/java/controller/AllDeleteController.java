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

/*
 * 全て削除
 */

@WebServlet("/all_delete")
public class AllDeleteController extends HttpServlet {
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
			
			// SQLに接続するための情報
			String url = "jdbc:mysql://localhost/todo";
			String user = "root";
			String password = "";
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		String sql_delete = "DELETE FROM posts";
		String sql_clear = "ALTER TABLE todo.posts auto_increment = 1";
		
		try (Connection connection = DriverManager.getConnection (url, user, password)
				){
			PreparedStatement statement;
			
			// テーブル内のデータを全て削除
			statement = connection.prepareStatement(sql_delete);
			statement.executeUpdate();
			
			// IDの連番をclear
			statement = connection.prepareStatement(sql_clear);
			statement.executeUpdate();
			
			request.setAttribute("message","全て削除しました");
				
		} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		String forward = "/list";
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
		dispatcher.forward(request, response);
	}
}
