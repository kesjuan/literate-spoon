package com.yourNewBank.Banking.controller;

import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepositController {
    @Autowired
    private DepositService depositService;

    @GetMapping("/accounts/{accountId}/deposits")
    public ResponseEntity<?> getAllDepositsForThisAccount(@PathVariable Long accountId ){

        return depositService.findAllDepositsForAccount(accountId);
    }

    @GetMapping("/deposits/{depositId}")
    public ResponseEntity<?> getDepositById(@PathVariable Long depositId){

        return depositService.findDepositById(depositId);
    }

    @PostMapping("accounts/{accountId}/deposits")
    public ResponseEntity<?> createDeposit(@PathVariable Long accountId, @RequestBody Deposit deposit){

        return depositService.createDeposit(accountId,deposit);
    }
    @PutMapping("/deposits/{depositId}")
    public ResponseEntity<?> updateDepositById(@PathVariable Long depositId, @RequestBody Deposit deposit){

        return depositService.updateDeposit(depositId,deposit);
    }
    @DeleteMapping("/deposits/{depositId}")
    public ResponseEntity<?> deleteDeposit(@PathVariable Long depositId){
        return depositService.deleteDepositById(depositId);
    }
}
