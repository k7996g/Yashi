package com.capgemini.test;

import java.math.BigDecimal;

import org.junit.Test;

import com.capgemini.exceptions.DuplicateMobileNumberException;
import com.capgemini.exceptions.InsufficientAmountException;
import com.capgemini.exceptions.InvalidMobileNumberException;
import com.capgemini.repo.WalletRepo;
import com.capgemini.repo.WalletRepoImpl;
import com.capgemini.service.WalletService;
import com.capgemini.service.WalletServiceImpl;

public class WalletServiceImplTest {

	WalletRepo walletRepo = new WalletRepoImpl();
	WalletService walletService = new WalletServiceImpl(walletRepo);
	
	/*
	 * CREATE ACCOUNT
	 * 1. when duplicate mobile number is entered throw exception
	 * 2. when valid info is passed create account successfully
	 */
	
	@Test(expected= com.capgemini.exceptions.DuplicateMobileNumberException.class)
	public void whenDuplicateMobileNumberPassedThrowException() throws DuplicateMobileNumberException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		
	}
	
	@Test
	public void whenValidInfoIsPassedCreateAccountSuccessfully() throws DuplicateMobileNumberException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));

	}
	
	/*
	 * WITHDRAWAL
	 * 1. when invalid mobile number is passed on withdraw throw exception
	 * 2. when insufficient amount in wallet throw exception
	 * 3. when valid info is passed withdraw amount successfully
	 */
	
	@Test(expected= com.capgemini.exceptions.InvalidMobileNumberException.class)
	public void whenInvalidMobileNumberIsPassedOnWithdrawThrowException() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.withdrawAmount("9854121414", new BigDecimal("100.00"));	
	}
	
	@Test(expected= com.capgemini.exceptions.InsufficientAmountException.class)
	public void whenInsufficientAmountInWalletThrowException() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.withdrawAmount("9854121412", new BigDecimal("200.00"));	
	}
	
	@Test
	public void whenValidInfoIsPassedWithdrawAmountSuccessfully() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.withdrawAmount("9854121412", new BigDecimal("100.00"));	

	}
	
	/*
	 * DEPOSIT
	 * 1. when invalid mobile number is passed on deposit throw exception
	 * 2. when valid info is passed deposit amount successfully
	 */
	
	@Test(expected= com.capgemini.exceptions.InvalidMobileNumberException.class)
	public void whenInvalidMobileNumberIsPassedOnDepositThrowException() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.depositAmount("9854121414", new BigDecimal("100.00"));	
	}
	
	@Test
	public void whenValidInfoIsPassedDepositAmountSuccessfully() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.depositAmount("9854121412", new BigDecimal("100.00"));	

	}
	
	/*
	 * FUND TRANSFER
	 * 1. when invalid mobile number is passed on fund Transfer throw exception
	 * 2. when insufficient amount in wallet on fund transfer throw exception
	 * 3. when valid info is passed withdraw and deposit amount successfully
	 */
	
	@Test(expected= com.capgemini.exceptions.InvalidMobileNumberException.class)
	public void whenInvalidMobileNumberIsPassedOnFundTransferThrowException() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.createAccount("suraj", "9854121413", new BigDecimal("100.00"));
		walletService.fundTransfer("9854121414","9854121413",new BigDecimal("100.00"));	
	}
	
	@Test(expected= com.capgemini.exceptions.InsufficientAmountException.class)
	public void whenInsufficientAmountInWalletOnFundTransferThrowException() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.createAccount("suraj", "9854121413", new BigDecimal("100.00"));
		walletService.fundTransfer("9854121412","9854121413",new BigDecimal("500.00"));	
	}
	
	
	@Test
	public void whenValidInfoIsPassedDepositandWithdrawAmountSuccessfully() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.createAccount("suraj", "9854121413", new BigDecimal("100.00"));
		walletService.fundTransfer("9854121412","9854121413",new BigDecimal("100.00"));	

	}
	
	/*
	 * SHOW BALANCE
	 * 1. when invalid mobile number is passed on show balance throw exception
	 * 2. when valid info is passed show balance successfully
	 */
	
	@Test(expected= com.capgemini.exceptions.InvalidMobileNumberException.class)
	public void whenInvalidMobileNumberIsPassedOnShowBalanceThrowException() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.showBalance("9854121414");	
	}
	
	@Test
	public void whenValidInfoIsPassedShowBalanceSuccessfully() throws InvalidMobileNumberException, DuplicateMobileNumberException, InsufficientAmountException {
		walletService.createAccount("sushil", "9854121412", new BigDecimal("100.00"));
		walletService.showBalance("9854121412");	


	}
	
	
	
	
	
	
	


	
}
