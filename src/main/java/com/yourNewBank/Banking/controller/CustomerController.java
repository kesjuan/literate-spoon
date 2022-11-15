package com.yourNewBank.Banking.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yourNewBank.Banking.model.Address;
import com.yourNewBank.Banking.model.Customer;
import com.yourNewBank.Banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    @Autowired
   private CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers(){
        return customerService.getAllMyCustomers();
    }

    @GetMapping("/accounts/{accountId}/customer")
    public ResponseEntity<?> getCustomerByAccount(@PathVariable Long accountId){
    return customerService.getCustomerByAccountId(accountId);
    }
    @GetMapping("/customers/{customerId}/accounts")
    public ResponseEntity<?> getAllOfThisCustomerAccounts(@PathVariable Long customerId){
    return customerService.findAllOfThisCustomerAccounts(customerId);
    }
    @GetMapping("customers/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId){
        return customerService.findCustomerById(customerId);
    }
    @PutMapping("customers/{customerId}")
    public ResponseEntity<?> updateThisCustomer(@PathVariable Long customerId, @RequestBody Customer customer){
        return customerService.updateCustomer(customerId, customer);
    }
}
