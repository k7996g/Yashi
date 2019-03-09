package com.capgemini.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SetupConnection {
	public Connection connection;

	public SetupConnection() throws SQLException {
		 connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
				"Capgemini123");
	}

}
