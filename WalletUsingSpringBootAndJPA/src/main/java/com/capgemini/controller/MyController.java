package com.capgemini.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.beans.Customer;
import com.capgemini.exceptions.InsufficientBalance;
import com.capgemini.exceptions.MobileNumbeDoesNotExist;
import com.capgemini.exceptions.MobileNumberAlreadyExist;
import com.capgemini.services.WalletServiceImpl;

@RestController
public class MyController {
	
	@Autowired
	private WalletServiceImpl walletServiceImpl;
	
	@RequestMapping(value="/getBalance/{mobileno}", method=RequestMethod.GET)
	public Customer getBalance(@PathVariable String mobileno) throws MobileNumbeDoesNotExist
	{
		 return walletServiceImpl.showBalance(mobileno);
	}
	
	@RequestMapping(path="/addCustomer/{name}/{mobile}/{balance}", method=RequestMethod.POST)
	public Customer createAccount(@PathVariable("name") String name, @PathVariable("mobile") String mobileno, @PathVariable("balance") BigDecimal balance) throws MobileNumbeDoesNotExist, MobileNumberAlreadyExist{
		return walletServiceImpl.createAccount(name, mobileno, balance);
	}
	
	@RequestMapping(path="/depositMoney/{mobileno}/{amount}")
	public Customer depositMoney(@PathVariable String mobileno, @PathVariable BigDecimal amount) throws MobileNumbeDoesNotExist
	{
		return walletServiceImpl.depositAmount(mobileno, amount);
	}
	
	@RequestMapping(path="/withdrawMoney/{mobileno}/{amount}")
	public Customer withdrawMoney(@PathVariable String mobileno, @PathVariable BigDecimal amount) throws InsufficientBalance, MobileNumbeDoesNotExist
	{
		return walletServiceImpl.withdrawAmount(mobileno, amount);
	}
	
	@RequestMapping(path="/fundTransfer/{sourcemobileno}/{destinationmobileno}/{amount}")
	public List<Customer> fundTransfer(@PathVariable String sourcemobileno, @PathVariable String destinationmobileno, @PathVariable BigDecimal amount ) throws InsufficientBalance, MobileNumbeDoesNotExist
	{
		return walletServiceImpl.fundTransfer(sourcemobileno, destinationmobileno, amount);
	}
	
}
