package com.cg.ibs.im.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OracleConnection {
	private static Connection connection;

	public static Connection callConnection() {

		if (null == connection) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
			String url = resourceBundle.getString("url");
			String user = resourceBundle.getString("user");
			String password = resourceBundle.getString("password");
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return connection;
	}
}
