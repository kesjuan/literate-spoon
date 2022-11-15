package com.yourNewBank.Banking.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.handler.ResponseHandler;
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
        try{
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
            return ResponseHandler.generateResponse(HttpStatus.OK, "Customer created", realCustomer);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public ResponseEntity<?> getAllMyCustomers(){
        try{
            verifyCustomers("Error fetching customers");
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", customerRepository.findAll());
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    public ResponseEntity<?> getCustomerByAccountId(Long accountId){
        try{
            verifyCustomers("Error finding customers");
            Optional<Account> account = accountRepository.findById(accountId);
           Long customerId = account.get().getCustomerId();
           Optional<Customer> customer =customerRepository.findById(customerId);
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", customer);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    public Customer getCustomerByAccountIdNotForController(Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        Long customerId = account.get().getCustomerId();
        Optional<Customer> customer =customerRepository.findById(customerId);
        Customer customer1 = customer.get();
        return customer1;
    }
    public ResponseEntity<?> findAllOfThisCustomerAccounts(Long customerId){
        try{
            verifyCustomer(customerId,"Error fetching customers accounts");
            Iterable<Account> account = accountRepository.getAccountsWithThisCustomerId(customerId);
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", account);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public ResponseEntity<?> findCustomerById( Long customerId){
        try{
            verifyCustomer(customerId,"Error fetching customer");
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", customerRepository.findById(customerId));
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    public ResponseEntity<?> updateCustomer(Long customerId, ObjectNode customer){
        try{
            verifyCustomer(customerId,"Error fetching customer");
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
            return ResponseHandler.generateResponse(HttpStatus.OK, "Customer updated");
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }


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
