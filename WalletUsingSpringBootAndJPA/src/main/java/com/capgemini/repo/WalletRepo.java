package com.capgemini.repo;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.beans.Customer;

public interface WalletRepo extends CrudRepository<Customer, String> {
}
