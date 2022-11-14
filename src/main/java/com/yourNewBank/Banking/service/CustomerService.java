package com.yourNewBank.Banking.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Address;
import com.yourNewBank.Banking.model.Customer;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.AddressRepository;
import com.yourNewBank.Banking.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class CustomerService {

    Logger log = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AddressRepository addressRepository;


    public ResponseEntity<?> createCustomer(ObjectNode customer ){
        if (customer.isEmpty()){
           throw new ResourceNotFoundException("Error");
        }
        Customer realCustomer = new Customer();
        realCustomer.setFirstName(customer.get("firstName").asText());
        realCustomer.setLastName(customer.get("lastName").asText());

        JsonNode address = customer.get("address");
        Address realAddress = new Address();
        realAddress.setStreetNumber(address.findValue("street_number").asText());
        realAddress.setStreetName(address.findValue("street_name").asText());
        realAddress.setCity(address.findValue("city").asText());
        realAddress.setState(address.findValue("state").asText());
        realAddress.setZip(address.findValue("zip").asText());
        addressRepository.save(realAddress);
        realCustomer.setAddress(realAddress);
        customerRepository.save(realCustomer);
            log.info("final address is "+ realAddress);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllMyCustomers(){
        verifyCustomers("Error fetching customers");
        return new ResponseEntity<>(customerRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> getCustomerByAccountId(Long accountId){
       Optional<Account> account = accountRepository.findById(accountId);
       verifyAccount(accountId,"Error finding account");

       Long customerId = account.get().getCustomerId();
       Optional<Customer> customer =customerRepository.findById(customerId);
       return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    public Customer getCustomerByAccountIdNotForController(Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        Long customerId = account.get().getCustomerId();
        Optional<Customer> customer =customerRepository.findById(customerId);
        Customer customer1 = customer.get();
        return customer1;
    }
    public ResponseEntity<?> findAllOfThisCustomerAccounts(Long customerId){

        //Optional<Customer> customer = customerRepository.findById(customerId);
        verifyCustomer(customerId,"Error fetching customers accounts");
       Iterable<Account> account = accountRepository.getAccountsWithThisCustomerId(customerId);
       return new ResponseEntity<>(account,HttpStatus.OK);
    }

    public ResponseEntity<?> findCustomerById( Long customerId){
        verifyCustomer(customerId,"Error fetching customer");
        return  new ResponseEntity<>(customerRepository.findById(customerId), HttpStatus.OK);
    }

    public ResponseEntity<?> updateCustomer(Long customerId, ObjectNode customer){
        verifyCustomer(customerId,"Error finding Customer");
        Customer realCustomer = new Customer();
        realCustomer.setId(customerId);
        realCustomer.setFirstName(customer.get("firstName").asText());
        realCustomer.setLastName(customer.get("lastName").asText());

        JsonNode address = customer.get("address");
        Address realAddress = new Address();
        Long addyId = customerRepository.findById(customerId).get().getAddress().getId();
        realAddress.setId(addyId);
        realAddress.setStreetNumber(address.findValue("street_number").asText());
        realAddress.setStreetName(address.findValue("street_name").asText());
        realAddress.setCity(address.findValue("city").asText());
        realAddress.setState(address.findValue("state").asText());
        realAddress.setZip(address.findValue("zip").asText());
        addressRepository.save(realAddress);
        realCustomer.setAddress(realAddress);
        customerRepository.save(realCustomer);
        log.info("final address is "+ realAddress);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    protected void verifyCustomer(long customerId, String message)throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
    protected void verifyCustomers(String message)throws ResourceNotFoundException {
        Iterable<Customer> customers = customerRepository.findAll();
        // ArrayList<Account> list = new ArrayList<>((Collection) account);
        long size = customers.spliterator().getExactSizeIfKnown();
        if (size == 0 ){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
    protected void verifyAccount(long accountId, String message)throws ResourceNotFoundException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }


}
