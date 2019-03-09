package com.capgemini.beans;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Wallet {

	@Column(name="Balance")
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
