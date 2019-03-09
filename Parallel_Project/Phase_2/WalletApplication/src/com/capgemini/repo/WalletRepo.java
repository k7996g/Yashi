package com.capgemini.repo;

import com.capgemini.beans.Customer;
import com.capgemini.exceptions.MobileNumberDoesNotExistException;

public interface WalletRepo {

	public boolean save(Customer customer);

	public Customer findOne(String mobileNumber) throws MobileNumberDoesNotExistException;
}
