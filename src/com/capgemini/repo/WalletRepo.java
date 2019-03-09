package com.capgemini.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

import com.capgemini.beans.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepo extends JpaRepository<Customer, String> {

	@Query("select c from Customer c")
	public List<Customer> findAll();

	@EntityGraph(value = "joined")
	@Query("select c from Customer c where c.wallet.balance <= ?1")
	public List<Customer> findbyBalance(BigDecimal bigDecimal);
}
