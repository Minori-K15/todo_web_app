package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnect {

	// 定義
	private static String DATABASES = "todo";
	private static String JDBC_URL = "jdbc:mysql://localhost/" + DATABASES;
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String messages = "";
	
	protected static void main (String[] args) {
		// ドライバー接続メソッド
		jdbc_driver(JDBC_DRIVER);
		
		
	}

	// ドライバー接続
	protected static String jdbc_driver(String jdbc_driver) {
		try {
			Class.forName(jdbc_driver);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("[ERROR] JDBCドライバーを読み込めませんでした" + e);
		}
		return jdbc_driver;
	}
	
	// DB接続
//	protected static void db_connect(String url, String user, String password, String sql) {
//		try {
//			Connection connection = DriverManager.getConnection(url, user, password);
//			PreparedStatement statement = connection.prepareStatement(sql);
//		} catch (Exception e) {
//		}
//	}
	
	// select
	protected static void db_select(String sql, String days, String priority, String title, String content) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet results = statement.executeQuery())
		{
			statement.setString(1, days);
			statement.setString(2, priority);
			statement.setString(3, title);
			statement.setString(4, content);
			statement.executeUpdate();
			messages = "タイトル: " + title + "の新規作成ができました";
		} catch (Exception e) {
			messages = "Exception: " + e.getMessage();
		}
	}
	
	// create
	protected static void db_create(String sql, String days, String priority, String title, String content) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setString(1, days);
			statement.setString(2, priority);
			statement.setString(3, title);
			statement.setString(4, content);
			statement.executeUpdate();
			messages = "タイトル: " + title + "の新規作成ができました";
		} catch (Exception e) {
			messages = "Exception: " + e.getMessage();
		}
	}
	
	// update
	protected static void db_update(String sql, String days, String priority, String title, String content) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setString(1, days);
			statement.setString(2, priority);
			statement.setString(3, title);
			statement.setString(4, content);
			statement.executeUpdate();
			messages = "タイトル: " + title + "の新規作成ができました";
		} catch (Exception e) {
			messages = "Exception: " + e.getMessage();
		}
	}
}
