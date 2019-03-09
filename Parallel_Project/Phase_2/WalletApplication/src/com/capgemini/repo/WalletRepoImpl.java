package com.capgemini.repo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.capgemini.beans.Customer;
import com.capgemini.beans.Wallet;
import com.capgemini.exceptions.MobileNumberDoesNotExistException;
import com.capgemini.util.LoadOracleDriver;
import com.capgemini.util.SetupConnection;

public class WalletRepoImpl implements WalletRepo {

	Customer customer = new Customer();
	//HashMap<String, Customer> map = new HashMap<>();

	@Override
	public boolean save(Customer customer) {

		/*
		 * if (map.containsKey(customer.getMobileNumber())) { return false; }
		 * map.put(customer.getMobileNumber(), customer); return true;
		 */

		new LoadOracleDriver();
		try {
			SetupConnection setupConnection = new SetupConnection();

			Statement statement = setupConnection.connection.createStatement();
			String sqlSelect = "SELECT * FROM account WHERE mobile_number='" + customer.getMobileNumber() + "'";
			ResultSet resultSet = statement.executeQuery(sqlSelect);
			if(resultSet.next()) {
				return false;
			}
			String mobileNumber = customer.getMobileNumber();
			String customerName = customer.getCustomerName();
			BigDecimal amount = new BigDecimal(customer.getWallet().toString());
			String sqlInsert = "INSERT INTO account VALUES('" + mobileNumber + "','" + customerName + "',"
					+ amount.doubleValue() + ")";
			int x = statement.executeUpdate(sqlInsert);
			if (x > 0) {
				System.out.println("Customer Added to Database Successfully: " + x);
				return true;
			} else {
				System.out.println("No Entry Added!");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Customer findOne(String mobileNumber) throws MobileNumberDoesNotExistException {

		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		new LoadOracleDriver();
		try {
			SetupConnection setupConnection = new SetupConnection();
			Statement statement = setupConnection.connection.createStatement();

			String sqlSelect = "SELECT * FROM account WHERE mobile_number='" + mobileNumber + "'";
			ResultSet resultSet = statement.executeQuery(sqlSelect);
			while (resultSet.next()) {
				customer.setMobileNumber(resultSet.getString(1));
				customer.setCustomerName(resultSet.getString(2));
				wallet.setAmount(resultSet.getBigDecimal(3));
				customer.setWallet(wallet);
			}
			return customer;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * for (Map.Entry<String, Customer> entry : map.entrySet()) {
		 * 
		 * if (entry.getValue().getMobileNumber().equals(mobileNumber)) { return
		 * entry.getValue(); } }
		 */
		throw new MobileNumberDoesNotExistException();
	}

}
