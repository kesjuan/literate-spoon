package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.handler.RestExceptionHandler;
import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Customer;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.CustomerRepository;
import com.yourNewBank.dto.AccountDto;
import com.yourNewBank.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private RestExceptionHandler restExceptionHandler;


    public ResponseEntity<?> findAllAccounts(){
        //if (){}
        //verifyAccount();
        return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> findAccountsById(Long accountId){
        verifyAccount(accountId,"sowwyyy no account here");
//       Optional<Account> account = accountRepository.findById(accountId);
//       Optional<Customer> customer = customerRepository.findById(account.get().getCustomer().getId());
//        CustomerDto customerDto = new CustomerDto(customer.get().getId(),customer.get().getFirstName(),customer.get().getLastName());
//        AccountDto accountDto = new AccountDto(account.get().getId(),account.get().getType(),account.get().getNickName(),
//                account.get().getRewards(),account.get().getBalance(),customerDto);
        return new ResponseEntity<>(accountRepository.findById(accountId), HttpStatus.OK);
    }

    public ResponseEntity<?> createAccount(Long customerId, Account account){
       Optional<Customer> customer = customerRepository.findById(customerId);
      // account.setNickNameP();

       account.setNickName(customer.get().getFirstName() + "'s " +account.getType().name().toLowerCase() + " account" );
       accountRepository.save(account);

//        customer.ifPresent(account::setCustomer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<?> updateAccount(Long accountId, Account account){
        account.setId(accountId);
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteThisAccount(Long accountId){
        accountRepository.deleteById(accountId);
        return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
    }
    protected void verifyAccount(long accountId, String message)throws ResourceNotFoundException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
           // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }

}
//if (customer.isPresent()){
//          account.setCustomer(customer.get());
//      }