package com.capgemini.util;

public class LoadOracleDriver {
	public LoadOracleDriver() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException cnf) {
			System.out.println("The class is not found! ");
		}
	}

}
