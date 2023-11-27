package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnect {

	// 定義
	private static String DATABASES = "todo";
	private static String JDBC_URL = "jdbc:mysql://localhost/" + DATABASES;
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String messages = "";
	
	//
	private static String DAYS = null;
	private static String PRIORITY = null;
	private static String TITLE = null;
	private static String CONTENT = null;
	
	
	protected static void main (String sql_Execute) {
		
		// データベース接続を行うメソッド
		Connection connection = getConnection();
		String sql = sql_Execute;
	}
	
	// DB接続メソッド
	private static Connection getConnection(){
		try{
			Class.forName(JDBC_DRIVER);
			try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
				return connection;
			}
		 } catch (SQLException e) {
			 throw new IllegalStateException("[ERROR] DB接続できませんでした" + e);
		}catch (Exception e) {
			throw new IllegalStateException("[ERROR] JDBCドライバーを読み込めませんでした" + e);
		}
	}
	
	// SELECT
	protected static void db_Select(Connection connection, String sql) {
		
		try (PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery())
		{
			statement.setString(1, DAYS);
			statement.setString(2, PRIORITY);
			statement.setString(3, TITLE);
			statement.setString(4, CONTENT);
			statement.executeUpdate();
			messages = "タイトル: " + TITLE + "の新規作成ができました";
		} catch (Exception e) {
			messages = "Exception: " + e.getMessage();
		}
	}
	
	// CREATE
	protected static void db_Create(Connection connection , String sql) {
		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet results = statement.executeQuery())
			{
				statement.setString(1, DAYS);
				statement.setString(2, PRIORITY);
				statement.setString(3, TITLE);
				statement.setString(4, CONTENT);
				statement.executeUpdate();
				messages = "タイトル: " + TITLE + "の新規作成ができました";
			} catch (Exception e) {
				messages = "Exception: " + e.getMessage();
			}
	}
	
	// UPDATE
	protected static void db_Update(String sql, String days, String priority, String title, String content) {
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
	
	// DELETE
	protected static void db_Delete(String sql, String days, String priority, String title, String content) {
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
