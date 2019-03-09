
package com.capgemini.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.beans.Customer;
import com.capgemini.beans.Wallet;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InvalidInputException;
import com.capgemini.repo.WalletRepo;

@Component(value = "walletService")
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepo repo;

	public Customer createAccount(Customer customer) {
		if (repo.findOne(customer.getMobileNo()) != null)
			throw new InvalidInputException("Account is Already Created");
		return repo.save(customer);
	}

	public Customer showBalance(String mobileNo) throws InvalidInputException {
		Customer customer = repo.findOne(mobileNo);
		if (customer == null)
			throw new InvalidInputException("Mobile number does not exist");
		return customer;
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws InsufficientBalanceException, InvalidInputException {

		Customer source = null;
		Customer target = null;

		source = repo.findOne(sourceMobileNo);
		target = repo.findOne(targetMobileNo);
		if (source == null)
			throw new InvalidInputException("Source mobile number does not exist");
		if (target == null)
			throw new InvalidInputException("Target mobile number does not exist");

		if (amount.compareTo(new BigDecimal(0)) == 0)
			throw new InvalidInputException("Enter valid Amount to transfer");
		if (amount.compareTo(source.getWallet().getBalance()) > 0)
			throw new InsufficientBalanceException("Insufficient Balance in the account " + sourceMobileNo);

		source = withdrawAmount(sourceMobileNo, amount);
		target = depositAmount(targetMobileNo, amount);
		return source;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {

		Customer customer = null;

		customer = repo.findOne(mobileNo);
		if (customer == null)
			throw new InvalidInputException("Mobile number does not exist");
		if (amount.compareTo(new BigDecimal(0)) == 0)
			throw new InsufficientBalanceException("Enter Valid Amount to Deposit");

		BigDecimal balance = customer.getWallet().getBalance().add(amount);
		customer.setWallet(new Wallet(balance));

		repo.save(customer);

		return customer;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount)
			throws InsufficientBalanceException, InvalidInputException {
		Customer customer = null;
		customer = repo.findOne(mobileNo);
		if (customer == null)
			throw new InvalidInputException("Mobile number does not exist");

		if (amount.compareTo(new BigDecimal(0)) == 0)
			throw new InsufficientBalanceException("Enter Valid Amount to Withdraw");

		if (amount.compareTo(customer.getWallet().getBalance()) > 0)
			throw new InsufficientBalanceException("Insufficient Balance");

		BigDecimal balance = customer.getWallet().getBalance().subtract(amount);
		customer.setWallet(new Wallet(balance));
		repo.save(customer);

		return customer;

	}

	@Override
	public boolean checkAccount(Customer customer) {
		Customer customer1 = repo.findOne(customer.getMobileNo());
		if (customer1 == null)
			throw new InvalidInputException("There is No Account created with this Mobile Number");
		return true;
	}

	@Override
	public List<Customer> printAllCustomer() {

		return repo.findAll();
	}

}