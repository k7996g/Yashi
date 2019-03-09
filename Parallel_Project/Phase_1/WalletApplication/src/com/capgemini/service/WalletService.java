package com.capgemini.service;

import java.math.BigDecimal;

import com.capgemini.beans.Customer;
import com.capgemini.exceptions.DuplicateMobileNumberException;
import com.capgemini.exceptions.InsufficientAmountException;
import com.capgemini.exceptions.InvalidMobileNumberException;

public interface WalletService {
	public Customer createAccount(String name, String mobileNumber, BigDecimal amount)
			throws DuplicateMobileNumberException;

	public Customer showBalance(String mobileNumber) throws InvalidMobileNumberException;

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientAmountException;

	public Customer depositAmount(String mobileNumber, BigDecimal amount) throws InvalidMobileNumberException;

	public Customer withdrawAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientAmountException;

}
