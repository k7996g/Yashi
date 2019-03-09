package com.capgemini.repo;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.beans.Customer;
import com.capgemini.exceptions.InvalidMobileNumberException;

public class WalletRepoImpl implements WalletRepo {

	Customer customer = new Customer();
	HashMap<String, Customer> map = new HashMap<>();

	@Override
	public boolean save(Customer customer) {
		if (map.containsKey(customer.getMobileNumber())) {
			return false;
		}
		map.put(customer.getMobileNumber(), customer);
		return true;

		/*
		 * for (String name : map.keySet()) {
		 * 
		 * String key = name.toString(); String value = map.get(name).toString();
		 * System.out.println(key + " " + value);
		 * 
		 * }
		 */
	}

	@Override
	public Customer findOne(String mobileNumber) throws InvalidMobileNumberException {

		for (Map.Entry<String, Customer> entry : map.entrySet()) {

			if (entry.getValue().getMobileNumber().equals(mobileNumber)) {
				return entry.getValue();
			}
		}
		throw new InvalidMobileNumberException();
	}

}
