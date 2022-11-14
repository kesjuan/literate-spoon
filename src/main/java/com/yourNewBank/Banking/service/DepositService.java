package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.DepositRepository;
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
        verifyAccount(accountId,"Account not found");
        Iterable<Deposit> deposit = depositRepository.findDepositsForAccount(accountId);

        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }
    public ResponseEntity<?> findDepositById( Long depositId){
        verifyDeposit(depositId,"Error fetching deposit with id");
        return new ResponseEntity<>(depositRepository.findById(depositId),HttpStatus.OK);
    }
    public ResponseEntity<?> createDeposit( Long accountId,  Deposit deposit){
        verifyAccount(accountId,"Error creating deposit: Account not found");
        Date date = new Date();
        String stringDate = date.toInstant().atZone(ZonedDateTime.now().getZone()).toString();
        int endOfDate = stringDate.indexOf("T");
        String finalDate = (String) stringDate.subSequence(0,endOfDate);
        deposit.setPayeeId(accountId);
        deposit.setTransactionDate(finalDate);
    depositRepository.save(deposit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<?> updateDeposit(Long depositId, Deposit deposit){
        verifyAccount(depositId,"Deposit id does not exist");
        deposit.setId(depositId);
        depositRepository.save(deposit);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> deleteDepositById( Long depositId){
        verifyDeposit(depositId,"Deposit id does not exist");
       depositRepository.deleteById(depositId);
       return new ResponseEntity<>(HttpStatus.OK);
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
