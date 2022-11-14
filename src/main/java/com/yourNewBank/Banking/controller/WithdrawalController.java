package com.yourNewBank.Banking.controller;

import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.model.Withdrawal;
import com.yourNewBank.Banking.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;

    @GetMapping("/accounts/{accountId}/withdrawals")
    public ResponseEntity<?> getAllWithdrawalsForThisAccount(@PathVariable Long accountId ){

        return withdrawalService.findAllWithdrawalsForAccount(accountId);
    }

    @GetMapping("/withdrawals/{withdrawalId}")
    public ResponseEntity<?> getWithdrawalById(@PathVariable Long withdrawalId){

        return withdrawalService.findWithdrawalById(withdrawalId);
    }

    @PostMapping("accounts/{accountId}/withdrawals")
    public ResponseEntity<?> createWithdrawal(@PathVariable Long accountId, @RequestBody Withdrawal withdrawal){

        return withdrawalService.createWithdrawal(accountId,withdrawal);
    }
    @PutMapping("/withdrawals/{withdrawalId}")
    public ResponseEntity<?> updateWithdrawalById(@PathVariable Long withdrawalId, @RequestBody Withdrawal withdrawal){

        return withdrawalService.updateWithdrawal(withdrawalId,withdrawal);
    }
    @DeleteMapping("/withdrawals/{withdrawalId}")
    public ResponseEntity<?> deleteWithdrawal(@PathVariable Long withdrawalId){
        return withdrawalService.deleteWithdrawalById(withdrawalId);
    }
}
