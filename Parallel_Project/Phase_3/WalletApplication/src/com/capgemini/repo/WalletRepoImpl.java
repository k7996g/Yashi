package com.capgemini.repo;

import javax.persistence.EntityManager;
import com.capgemini.beans.Customer;
import com.capgemini.exceptions.MobileNumberDoesNotExistException;
import com.capgemini.util.JPAUtil;

public class WalletRepoImpl implements WalletRepo {

	Customer customer = new Customer();
	private EntityManager entityManager;

	public WalletRepoImpl() {
		entityManager = JPAUtil.getEntityManager();
	}

	public boolean updateAccount(Customer updatedCustomer) {

		entityManager.getTransaction().begin();

		Customer customer = entityManager.find(Customer.class, updatedCustomer.getMobileNumber());

		customer.getWallet().setAmount(updatedCustomer.getWallet().getAmount());

		entityManager.getTransaction().commit();

		return true;
	}

	@Override
	public boolean updateAccount(Customer sender, Customer receiver) {

		entityManager.getTransaction().begin();

		Customer customer1 = entityManager.find(Customer.class, sender.getMobileNumber());
		customer1.getWallet().setAmount(sender.getWallet().getAmount());

		Customer customer2 = entityManager.find(Customer.class, receiver.getMobileNumber());
		customer2.getWallet().setAmount(receiver.getWallet().getAmount());

		entityManager.getTransaction().commit();

		return true;

	}

	@Override
	public boolean save(Customer customer) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(customer);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Customer findOne(String mobileNumber) throws MobileNumberDoesNotExistException {

		Customer customer = entityManager.find(Customer.class, mobileNumber);
		if(customer==null) {
			throw new MobileNumberDoesNotExistException();
		}
		return customer;
	}

	@Override
	public void update(Customer customer) {
		entityManager.getTransaction().begin();
		entityManager.merge(customer);
		entityManager.getTransaction().commit();

	}

}
