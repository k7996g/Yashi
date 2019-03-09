package com.capgemini.service;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.beans.Customer;
import com.capgemini.beans.Wallet;
import com.capgemini.exceptions.DuplicateMobileNumberException;
import com.capgemini.exceptions.InsufficientAmountException;
import com.capgemini.exceptions.InvalidInputException;
import com.capgemini.exceptions.MobileNumberDoesNotExistException;
import com.capgemini.repo.WalletRepo;

public class WalletServiceImpl implements WalletService {

	WalletRepo walletRepo;

	public WalletServiceImpl(WalletRepo walletRepo) {
		super();
		this.walletRepo = walletRepo;
	}

	/*--------------CREATE ACCOUNT--------------------*/
	@Override
	public Customer createAccount(String name, String mobileNumber, BigDecimal amount)
			throws DuplicateMobileNumberException {
		Wallet wallet = new Wallet(amount);
		Customer customer = new Customer(name, mobileNumber, wallet);
		if (walletRepo.save(customer)) {

			return customer;
		}

		throw new DuplicateMobileNumberException();
	}

	/*--------------DEPOSIT AMOUNT--------------------*/
	@Override
	public Customer depositAmount(String mobileNumber, BigDecimal amount) throws MobileNumberDoesNotExistException {
		Customer customer = walletRepo.findOne(mobileNumber);
		Wallet wallet = customer.getWallet();
		wallet.setAmount(wallet.getAmount().add(amount));
		customer.setWallet(wallet);
		return customer;
	}

	/*--------------WITHDRAW AMOUNT--------------------*/
	@Override
	public Customer withdrawAmount(String mobileNumber, BigDecimal amount)
			throws MobileNumberDoesNotExistException, InsufficientAmountException {
		Customer customer = walletRepo.findOne(mobileNumber);
		Wallet wallet = customer.getWallet();
		if (wallet.getAmount().compareTo(amount) < 0) {
			throw new InsufficientAmountException();

		}
		wallet.setAmount(wallet.getAmount().subtract(amount));
		customer.setWallet(wallet);
		return customer;

	}

	/*--------------SHOW BALANCE--------------------*/
	@Override
	public Customer showBalance(String mobileNumber) throws MobileNumberDoesNotExistException {
		return walletRepo.findOne(mobileNumber);
	}

	/*--------------FUND TRANSFER--------------------*/
	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws MobileNumberDoesNotExistException, InsufficientAmountException {
		Customer sourceCustomer = walletRepo.findOne(sourceMobileNo);
		Customer targetCustomer = walletRepo.findOne(targetMobileNo);
		withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		return sourceCustomer;

	}

	/*--------------VALIDATE NAME--------------------*/
	@Override
	public boolean ValidateName(String name) throws InvalidInputException {
		if (name == null)
			throw new InvalidInputException("Name field cannot be empty");
		Pattern pat = Pattern.compile("[A-Z][A-Za-z ]{2,20}");
		Matcher mat = pat.matcher(name);
		return mat.matches();
	}

	/*--------------VALIDATE MOBILE NUMBER --------------------*/
	@Override
	public boolean ValidateMobNo(String mobileNumber) throws InvalidInputException {
		if (mobileNumber == null)
			throw new InvalidInputException("Number connot be zero");
		Pattern pat = Pattern.compile("[6-9][0-9]{9}");
		Matcher mat = pat.matcher(mobileNumber);
		return mat.matches();

	}
	
	/*--------------VALIDATE AMOUNT--------------------*/
	@Override
	public boolean ValidateAmount(BigDecimal amount) throws InvalidInputException {
		
	if(amount.compareTo( BigDecimal.valueOf( 0 )) > 0 
		     && amount.compareTo( BigDecimal.valueOf( 100000 )) < 0)
		return true;
	return false;
	}
}
