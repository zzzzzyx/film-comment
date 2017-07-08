package com.zzzzzyx.film_comment.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

	private static final String public_url = "jdbc:mysql://www.zzzzzyx.com:3306/moskwa?useSSL=false";
	private static final String public_user = "root";
	private static final String public_password = "mypassword";
	protected Connection conn = null;
	private static final String driverName = "com.mysql.jdbc.Driver";

	public Util() {
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(public_url, public_user, public_password);// 获取连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return conn;
	}

	private PreparedStatement pst = null;

	public void prepareSqlStatement(String sql) throws SQLException {
		pst = conn.prepareStatement(sql);
	}

	public ResultSet executePreparedSql() throws SQLException {
		ResultSet rs = pst.executeQuery();
		return rs;
	}

	public ResultSet executeSql(String sql) throws SQLException {
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(sql);
		return rs;
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
