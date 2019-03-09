package com.capgemini.beans;

public class Customer {

	private String customerName;
	private String mobileNumber;
	private Wallet wallet;

	public Customer() {
		super();
	}


	public Customer(String customerName, String mobileNumber, Wallet wallet) {
		super();
		this.customerName = customerName;
		this.mobileNumber = mobileNumber;
		this.wallet = wallet;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}


	@Override
	public String toString() {
		return "CustomerName= " + customerName + ", MobileNumber= " + mobileNumber + ", Wallet= " + wallet ;
	}
	

}
