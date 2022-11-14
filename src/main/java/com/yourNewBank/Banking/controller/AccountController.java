package com.yourNewBank.Banking.controller;

import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts(){

        return accountService.findAllAccounts();
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable Long accountId){

        return accountService.findAccountsById(accountId);
    }

    @PostMapping("/customers/{customerId}/accounts")
    public ResponseEntity<?> createAccount(@PathVariable Long customerId,@RequestBody Account account){

        return accountService.createAccount(customerId,account);
    }
    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<?> updateAccountById(@PathVariable Long accountId, @RequestBody Account account){

        return accountService.updateAccount(accountId,account);
    }
    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountId){
        return accountService.deleteThisAccount(accountId);
    }

}
