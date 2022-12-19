package model.dao;

import java.sql.*;

public class DAO {
	static protected final String DRIVER = "com.mysql.cj.jdbc.Driver";
	static protected final String URL = "jdbc:mysql://localhost:3306/acoeko_test";
	static protected final String USER = "root";
	static protected final String PASSWORD = "4313";
	
	protected Connection conn = null;
	protected ResultSet rs = null;
	protected PreparedStatement ptmt = null;
	protected Statement	stmt = null;
	
	protected DAO() throws ClassNotFoundException {
		try {
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}
	protected Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return conn;
	}
	
	public void close(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}catch (SQLException e) {				
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			}catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
			}
		}
	}
	
	static void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}catch (SQLException e) {				
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			}catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
			}
		}
	}
}
