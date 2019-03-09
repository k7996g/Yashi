package com.capgemini.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.beans.*;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InvalidInputException;
import com.capgemini.service.WalletService;

@Controller
public class CustomerActionController {
	@Autowired
	private WalletService walletService;

	@RequestMapping(value = "/registerCustomer")
	public ModelAndView registerCustomer(@Valid @ModelAttribute("customer") Customer cus, BindingResult result) {
		Customer customer = null;
		try {
			if (result.hasErrors())
				return new ModelAndView("RegistrationPage");
			customer = walletService.createAccount(cus);
		} catch (InvalidInputException e) {
			return new ModelAndView("errorPage", "message", e.getMessage());
		}
		return new ModelAndView("registrationSuccessPage", "customer", customer);
	}

	@RequestMapping(value = "/validate")
	public ModelAndView loginCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result) {
		boolean bool;
		try {
			bool = walletService.checkAccount(customer);
		} catch (InvalidInputException e) {
			return new ModelAndView("error", "message", e.getMessage());
		}
		if (bool)
			return new ModelAndView("Myaccount");
		return new ModelAndView("LoginPage");
	}

	@RequestMapping(value = "/showbalance")
	public ModelAndView showBalance(@Valid @RequestParam("mobileNo") String mobile) {
		Customer customer = null;
		try {
			customer = walletService.showBalance(mobile);
		} catch (InvalidInputException | InsufficientBalanceException e) {
			return new ModelAndView("errorPage", "message", e.getMessage());
		}

		return new ModelAndView("BalanceDisplay", "balance", customer.getWallet().getBalance());

	}

	@RequestMapping(value = "/Withdrawamount")
	public ModelAndView withdrawCustomer(@Valid @RequestParam("mobileNo") String mobile,
			@RequestParam("amount") BigDecimal amount) {
		Customer customer = null;
		try {

			customer = walletService.withdrawAmount(mobile, amount);
		} catch (InvalidInputException | InsufficientBalanceException e) {
			return new ModelAndView("errorPage", "message", e.getMessage());
		}

		return new ModelAndView("WithdrawSuccessPage", "customer", customer);

	}

	@RequestMapping(value = "/Deposit")
	public ModelAndView depositCustomer(@Valid @RequestParam("mobileNo") String mobile,
			@RequestParam("amount") BigDecimal amount) {
		Customer customer = null;
		try {
			customer = walletService.depositAmount(mobile, amount);
		} catch (InvalidInputException | InsufficientBalanceException e) {
			return new ModelAndView("errorPage", "message", e.getMessage());
		}

		return new ModelAndView("DepositSuccessPage", "customer", customer);

	}

	@RequestMapping(value = "/TransferFunds")
	public ModelAndView TransferFundsCustomer(@Valid @RequestParam("mobileNo1") String mobile1,
			@Valid @RequestParam("mobileNo2") String mobile2, @RequestParam("amount") BigDecimal amount) {
		Customer customer = null;
		try {
			customer = walletService.fundTransfer(mobile1, mobile2, amount);
		} catch (InvalidInputException | InsufficientBalanceException e) {
			return new ModelAndView("errorPage", "message", e.getMessage());
		}

		return new ModelAndView("TransferSuccessPage", "customer", customer);

	}

	@RequestMapping(value = "/printAll")
	public ModelAndView printAllCustomer() {
		List<Customer> list = new ArrayList<>();

		try {
			list = walletService.printAllCustomer();
		} catch (Exception e) {
			return new ModelAndView("errorPage", "message", e.getMessage());
		}

		return new ModelAndView("PrintAllCustomers", "list", list);
	}

}
