package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.handler.ResponseHandler;
import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.model.Withdrawal;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.WithdrawalRepository;
import com.yourNewBank.enums.IMedium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<?> findAllWithdrawalsForAccount(Long accountId){
        try{
            verifyAccount(accountId,"Account not found");
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", withdrawalRepository.findWithdrawalsForAccount(accountId));
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> findWithdrawalById( Long withdrawalId){
        try{
            verifyWithdrawal(withdrawalId,"Error fetching withdrawal with id");
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", withdrawalRepository.findById(withdrawalId));
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> createWithdrawal( Long accountId,  Withdrawal withdrawal){
        try{
            verifyAccount(accountId,"Error creating withdrawal: Account not found");
            Date date = new Date();
            String stringDate = date.toInstant().atZone(ZonedDateTime.now().getZone()).toString();
            int endOfDate = stringDate.indexOf("T");
            String finalDate = (String) stringDate.subSequence(0,endOfDate);
            withdrawal.setPayerId(accountId);
            withdrawal.setTransactionDate(finalDate);
            Optional<Account> account = accountRepository.findById(accountId);
            if (withdrawal.getMedium() == IMedium.Balance){
                account.get().withdrawalBalance(withdrawal.getAmount());
            }
            if (withdrawal.getMedium() == IMedium.Rewards){
                account.get().withdrawalRewards(withdrawal.getAmount());
                withdrawalRepository.save(withdrawal);
            }
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "Withdrawal created", withdrawal);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }
    public ResponseEntity<?> updateWithdrawal(Long withdrawalId, Withdrawal withdrawal){
        try{
            verifyWithdrawal(withdrawalId,"Withdrawal id does not exist");
            withdrawal.setId(withdrawalId);
            withdrawalRepository.save(withdrawal);
            return ResponseHandler.generateResponse(HttpStatus.OK, "Withdrawal updated", withdrawal);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> deleteWithdrawalById( Long withdrawalId){
        try{
            verifyWithdrawal(withdrawalId,"Withdrawal id does not exist");
            withdrawalRepository.deleteById(withdrawalId);
            return new ResponseEntity<> (HttpStatus.NO_CONTENT);
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
    protected void verifyWithdrawal(long withdrawalId, String message)throws ResourceNotFoundException {
        Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
        if (withdrawal.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
}
