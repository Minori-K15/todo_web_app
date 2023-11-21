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
	private static final String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";	
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		// jdbcドライバーに接続
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// sqlコマンド
		String sql_delete = "DELETE FROM posts";
		String sql_clear = "ALTER TABLE todo.posts auto_increment = 1";
		
		// DB接続
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD)
				){
			PreparedStatement statement;
			
			// テーブル内のデータを全て削除
			statement = connection.prepareStatement(sql_delete);
			statement.executeUpdate();
			
			// IDの連番をclear
			statement = connection.prepareStatement(sql_clear);
			statement.executeUpdate();
			
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
}
