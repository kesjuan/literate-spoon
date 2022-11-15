package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.handler.ResponseHandler;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    //private RestExceptionHandler restExceptionHandler;


    public ResponseEntity<?> findAllAccounts(){
        try{
            verifyAccounts("Error fetching accounts");
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", accountRepository.findAll());
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    public ResponseEntity<?> findAccountsById(Long accountId){
        try {
            verifyAccount(accountId,"Error fetching account");
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", accountRepository.findById(accountId));
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage() );
        }
    }

    public ResponseEntity<?> createAccount(Long customerId, Account account){

        try {
            verifyCustomer(customerId, "Error creating customers account");
           Optional<Customer> customer = customerRepository.findById(customerId);
           account.setNickName(customer.get().getFirstName() + "'s " + account.getType().name().toLowerCase() + " account");
           accountRepository.save(account);
           return ResponseHandler.generateResponse(HttpStatus.CREATED, "Account created", account);
       }catch(ResourceNotFoundException e){
           return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage() );
       }

    }
    public ResponseEntity<?> updateAccount(Long accountId, Account account){
        try{
            verifyAccount(accountId,"Error");
            account.setId(accountId);
            accountRepository.save(account);
            return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, "Customer account updated");
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    public ResponseEntity<?> deleteThisAccount(Long accountId){
        try{
            verifyAccount(accountId,"Account does not exist");
            accountRepository.deleteById(accountId);
            return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, "Account successfully deleted");
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    protected void verifyAccount(long accountId, String message)throws ResourceNotFoundException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
           // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
    protected void verifyAccounts(String message)throws ResourceNotFoundException {
        Iterable<Account> account = accountRepository.findAll();
       // ArrayList<Account> list = new ArrayList<>((Collection) account);
       long size = account.spliterator().getExactSizeIfKnown();
        if (size == 0 ){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
    protected void verifyCustomer(long customerId, String message)throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }

}
//if (customer.isPresent()){
//          account.setCustomer(customer.get());
//      }