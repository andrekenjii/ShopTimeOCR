package dao;

import java.sql.*;

public class DB {
	private static String db_connection_string = "jdbc:sqlserver://localhost:1433;" + "databaseName=shoptimedb;";
	private static String db_user = "shoptime-user";
	private static String db_password = "123456";
	private Connection conn = null;

	public Connection OpenConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.conn = DriverManager.getConnection(db_connection_string, db_user, db_password);
		} catch (Exception e) {
			e.printStackTrace();
			this.conn = null;
		}
		return this.conn;
	}

	public void ClosedConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}