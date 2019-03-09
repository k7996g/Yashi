package com.capgemini.service;

import java.math.BigDecimal;

import com.capgemini.beans.Customer;
import com.capgemini.exceptions.DuplicateMobileNumberException;
import com.capgemini.exceptions.InsufficientAmountException;
import com.capgemini.exceptions.InvalidInputException;
import com.capgemini.exceptions.MobileNumberDoesNotExistException;

public interface WalletService {
	public Customer createAccount(String name, String mobileNumber, BigDecimal amount)
			throws DuplicateMobileNumberException;

	public Customer showBalance(String mobileNumber) throws MobileNumberDoesNotExistException;

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws MobileNumberDoesNotExistException, InsufficientAmountException;

	public Customer depositAmount(String mobileNumber, BigDecimal amount) throws MobileNumberDoesNotExistException;

	public Customer withdrawAmount(String mobileNumber, BigDecimal amount)
			throws MobileNumberDoesNotExistException, InsufficientAmountException;

	boolean ValidateAmount(BigDecimal amount) throws InvalidInputException;

	boolean ValidateMobNo(String mobileNumber) throws InvalidInputException;

	boolean ValidateName(String name) throws InvalidInputException;

}
