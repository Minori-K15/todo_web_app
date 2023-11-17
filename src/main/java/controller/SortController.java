package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SortController
 */
@WebServlet("/sort")
public class SortController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// 定義
	private static String JDBC_URL = "jdbc:mysql://localhost/todo";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	// private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// valueチェック
		String select = request.getParameter("select");
		if(select == null ) {
			request.setAttribute("messsage", "[ERROR]ソートできません");
		}
		
		String set_Select = select;
		
		// コマンド選択
		String sql = sort(select);
		
		// ドライバー読み込み
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("[ERROR] JDBCドライバーを読み込めませんでした" + e);
		}
		
		// DBログイン
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery()){
			
			// 取得したデータをリスト化
			ArrayList<HashMap<String, String>> rows = new
			ArrayList<HashMap<String, String>> ();
			
			while (results.next()) {
				HashMap<String, String> conlums = new
				HashMap<String, String> ();
				
				String id = results.getString("id");
				conlums.put("id", id);
				
				String days = results.getString("days");
				conlums.put("days", days);

				String limit = results.getString("limit");
				conlums.put("limit", limit);
				
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
				
				conlums.put("priority", priority);
				
				String title = results.getString("title");
				conlums.put("title", title);
				
				String content = results.getString("content");
				conlums.put("content", content);
				
				rows.add(conlums);
			}
			System.out.println(rows);
			return_slect(set_Select);
			request.setAttribute("select", return_slect(set_Select));
			request.setAttribute("rows", rows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// list.jspにリダイレクト
		String forward = "/list";
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
		dispatcher.forward(request, response);
	}
	
	// ソートコマンド選択
	private static String sort(String select) {
		switch (select) {
		case "days_asce":
			return "SELECT * FROM posts ORDER BY days";
			
		case "days_desc":
			return "SELECT * FROM posts ORDER BY days DESC";
		
		case "limit_asce":
			return "SELECT * FROM posts ORDER BY `limit`";
			
		case "limit_desc":
			return "SELECT * FROM posts ORDER BY `limit` DESC";
			
		case "priority_asce":
			return "SELECT * FROM posts ORDER BY priority";

		case "priority_desc":
			return "SELECT * FROM posts ORDER BY priority DESC";
		}
		return select;
	}
	
	// ソートコマンド選択
	private static String return_slect(String return_select) {
		switch (return_select) {
		case "days_asce":
			return "days_asce";
			
		case "days_desc":
			return "days_desc";
		
		case "limit_asce":
			return "limit_asce";
			
		case "limit_desc":
			return "limit_desc";
			
		case "priority_asce":
			return "priority_asce";

		case "priority_desc":
			return "priority_desc";
		}
		return return_select;
	}
}
