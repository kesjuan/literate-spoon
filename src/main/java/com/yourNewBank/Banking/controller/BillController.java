package com.yourNewBank.Banking.controller;

import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Bill;
import com.yourNewBank.Banking.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {
    @Autowired
    private BillService billService;


    @GetMapping("/accounts/{accountId}/bills")
    public ResponseEntity<?> getAllBillsForThisAccount(@PathVariable Long accountId){

        return billService.findAllBillsForThisAccount(accountId);
    }

    @GetMapping("/bills/{billId}")
    public ResponseEntity<?> getBillById(@PathVariable Long billId){

        return billService.findBillById(billId);
    }
    @GetMapping("/customers/{customerId}/bills")
    public ResponseEntity<?> getBillByCustomer(@PathVariable Long customerId){

        return billService.findBillByCustomer(customerId);
    }


    @PostMapping("/accounts/{accountId}/bills")
    public ResponseEntity<?> createBills(@PathVariable Long accountId,@RequestBody Bill bill){

        return billService.createBillForThisAccount(accountId,bill);
    }

    @PutMapping("/bills/{billId}")
    public ResponseEntity<?> updateBillById(@PathVariable Long billId, @RequestBody Bill bill){

        return billService.updateBillById(billId,bill);
    }

    @DeleteMapping("/bills/{billId}")
    public ResponseEntity<?> deleteBillById(@PathVariable Long billId){
        return billService.deleteBillById(billId);
    }
}
