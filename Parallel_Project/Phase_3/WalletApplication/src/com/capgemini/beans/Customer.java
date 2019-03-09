package com.capgemini.beans;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer {

	@Column(name="Name")
	private String customerName;

	@Id
	@Column(name="Mobile")
	private String mobileNumber;

	@Embedded
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
		return "CustomerName= " + customerName + ", MobileNumber= " + mobileNumber + ", Wallet= " + wallet;
	}

}
