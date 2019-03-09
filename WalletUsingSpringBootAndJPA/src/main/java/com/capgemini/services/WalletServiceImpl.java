package com.capgemini.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.beans.Customer;
import com.capgemini.beans.Wallet;
import com.capgemini.exceptions.InsufficientBalance;
import com.capgemini.exceptions.MobileNumbeDoesNotExist;
import com.capgemini.exceptions.MobileNumberAlreadyExist;
import com.capgemini.repo.WalletRepo;

@Service
public class WalletServiceImpl implements WalletService{

	@Autowired
	WalletRepo walletRepo;
	
	
	public WalletServiceImpl(WalletRepo walletRepo) {
		super();
		this.walletRepo = walletRepo;
	}

	@Override
	public Customer createAccount(String name, String mobileno, BigDecimal balance) throws MobileNumbeDoesNotExist, MobileNumberAlreadyExist {
		// TODO Auto-generated method stub
		//if(showBalance(mobileno)!=null)
		//	throw new MobileNumberAlreadyExist();
		Customer customer= new Customer();
		Wallet wallet=new Wallet();
		wallet.setBalance(balance);
		customer.setName(name);
		customer.setMobileno(mobileno);
		customer.setWallet(wallet);
		walletRepo.save(customer);
		return customer;
	}

	@Override
	public Customer showBalance(String mobileno) throws MobileNumbeDoesNotExist  {
		// TODO Auto-generated method stub
		Customer customer= walletRepo.findById(mobileno).get();
		if(customer==null)
			throw new MobileNumbeDoesNotExist();
		return customer;
	}

	@Override
	public List<Customer> fundTransfer(String sourceMobileno, String destinationMobileno, BigDecimal amount) throws InsufficientBalance, MobileNumbeDoesNotExist {
		// TODO Auto-generated method stub
		List<Customer> l=new ArrayList<>();
		Customer c1=withdrawAmount(sourceMobileno, amount);
		walletRepo.save(c1);
		Customer c2=depositAmount(destinationMobileno, amount);
		walletRepo.save(c2);
		l.add(c1);
		l.add(c2);
		return l;
	}

	@Override
	public Customer depositAmount(String mobileno, BigDecimal amount) throws MobileNumbeDoesNotExist {
		// TODO Auto-generated method stub
		Customer customer=new Customer();
		Wallet wallet=new Wallet();
		customer =showBalance(mobileno);
		wallet=customer.getWallet();
		wallet.setBalance(wallet.getBalance().add(amount));
		customer.setWallet(wallet);
		walletRepo.save(customer);
		return customer;
	}

	@Override
	public Customer withdrawAmount(String mobileno, BigDecimal amount) throws InsufficientBalance, MobileNumbeDoesNotExist{
		// TODO Auto-generated method stub
		Customer customer=showBalance(mobileno);
		Wallet wallet=customer.getWallet();
		int res=wallet.getBalance().compareTo(amount);
		if(res==-1)
		{
			throw new InsufficientBalance();
		}
		wallet.setBalance(wallet.getBalance().subtract(amount));
		customer.setWallet(wallet);	
		walletRepo.save(customer);
		return customer;
	}

}
