package com.capgemini.service;

import java.math.BigDecimal;
import com.capgemini.beans.Customer;
import com.capgemini.beans.Wallet;
import com.capgemini.exceptions.DuplicateMobileNumberException;
import com.capgemini.exceptions.InsufficientAmountException;
import com.capgemini.exceptions.InvalidMobileNumberException;
import com.capgemini.repo.WalletRepo;

public class WalletServiceImpl implements WalletService {

	WalletRepo walletRepo;

	public WalletServiceImpl(WalletRepo walletRepo) {
		super();
		this.walletRepo = walletRepo;
	}

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

	@Override
	public Customer depositAmount(String mobileNumber, BigDecimal amount) throws InvalidMobileNumberException {
		Customer customer = walletRepo.findOne(mobileNumber);
		Wallet wallet = customer.getWallet();
		wallet.setAmount(wallet.getAmount().add(amount));
		customer.setWallet(wallet);
		return customer;
	}

	@Override
	public Customer withdrawAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientAmountException {
		Customer customer = walletRepo.findOne(mobileNumber);
		Wallet wallet = customer.getWallet();
		if (wallet.getAmount().compareTo(amount) < 0) {
			throw new InsufficientAmountException();

		}
		wallet.setAmount(wallet.getAmount().subtract(amount));
		customer.setWallet(wallet);
		return customer;

	}

	@Override
	public Customer showBalance(String mobileNumber) throws InvalidMobileNumberException {
		return walletRepo.findOne(mobileNumber);
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientAmountException {
		Customer sourceCustomer = walletRepo.findOne(sourceMobileNo);
		walletRepo.findOne(targetMobileNo);
		withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		return sourceCustomer;

	}
}
