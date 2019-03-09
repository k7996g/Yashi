package com.capgemini.beans;

import java.math.BigDecimal;

public class Wallet {

	private BigDecimal amount;

	public Wallet() {
		super();
	}

	public Wallet(BigDecimal amount) {
		super();
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return ""+amount ;
	}
	

}
