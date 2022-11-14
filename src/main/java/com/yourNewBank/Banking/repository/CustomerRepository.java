package com.yourNewBank.Banking.repository;

import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

//    @Query(value = "select * from account where customer_id = ?1", nativeQuery = true)
//    public Iterable<Account> getAccountsWithThisCustomerId(Long customerId);
}