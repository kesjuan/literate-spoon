package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.handler.ResponseHandler;
import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.DepositRepository;
import com.yourNewBank.enums.IMedium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class DepositService {

    @Autowired
   private DepositRepository depositRepository;

    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<?> findAllDepositsForAccount(Long accountId){
        try{
            verifyAccount(accountId,"Account not found");
            Iterable<Deposit> deposit = depositRepository.findDepositsForAccount(accountId);
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", deposit);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> findDepositById( Long depositId){
        try{
            verifyDeposit(depositId,"Error fetching deposit with id");
            return ResponseHandler.generateResponse(HttpStatus.OK, "Success", depositRepository.findById(depositId));
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> createDeposit( Long accountId,  Deposit deposit){
        try{
            verifyAccount(accountId,"Error creating deposit: Account not found");
            Date date = new Date();
            String stringDate = date.toInstant().atZone(ZonedDateTime.now().getZone()).toString();
            int endOfDate = stringDate.indexOf("T");
            String finalDate = (String) stringDate.subSequence(0,endOfDate);
            deposit.setPayeeId(accountId);
            deposit.setTransactionDate(finalDate);
            Optional<Account> account = accountRepository.findById(accountId);
            if (deposit.getMedium() == IMedium.Balance){
                account.get().depositToBalance(deposit.getAmount());
            }
            if (deposit.getMedium() == IMedium.Rewards){
                account.get().depositToRewards(deposit.getAmount());
            }
            depositRepository.save(deposit);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "Created deposit and added it to the account", deposit);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> updateDeposit(Long depositId, Deposit deposit){
        try{
            verifyAccount(depositId,"Deposit id does not exist");
            deposit.setId(depositId);
            depositRepository.save(deposit);
            //need to get  202 http status
            return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, "Accepted deposit modification");
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> deleteDepositById( Long depositId){
        try{
            verifyDeposit(depositId,"Deposit id does not exist");
            depositRepository.deleteById(depositId);
            //need to get  202 http status
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    protected void verifyDeposit(long depositId, String message)throws ResourceNotFoundException {
        Optional<Deposit> deposit = depositRepository.findById(depositId);
        if (deposit.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
}
