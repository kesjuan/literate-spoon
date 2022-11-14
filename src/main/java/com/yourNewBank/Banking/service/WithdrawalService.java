package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.model.Withdrawal;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.WithdrawalRepository;
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
        verifyAccount(accountId,"Account not found");
        Iterable<Withdrawal> withdrawals = withdrawalRepository.findWithdrawalsForAccount(accountId);

        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
    public ResponseEntity<?> findWithdrawalById( Long withdrawalId){
        verifyWithdrawal(withdrawalId,"Error fetching withdrawal with id");
        return new ResponseEntity<>(withdrawalRepository.findById(withdrawalId),HttpStatus.OK);
    }
    public ResponseEntity<?> createWithdrawal( Long accountId,  Withdrawal withdrawal){
        verifyAccount(accountId,"Error creating withdrawal: Account not found");
        Date date = new Date();
        String stringDate = date.toInstant().atZone(ZonedDateTime.now().getZone()).toString();
        int endOfDate = stringDate.indexOf("T");
        String finalDate = (String) stringDate.subSequence(0,endOfDate);
        withdrawal.setPayerId(accountId);
        withdrawal.setTransactionDate(finalDate);
        withdrawalRepository.save(withdrawal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<?> updateWithdrawal(Long withdrawalId, Withdrawal withdrawal){
        verifyWithdrawal(withdrawalId,"Withdrawal id does not exist");
        withdrawal.setId(withdrawalId);
        withdrawalRepository.save(withdrawal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> deleteWithdrawalById( Long withdrawalId){
        verifyWithdrawal(withdrawalId,"Withdrawal id does not exist");
        withdrawalRepository.deleteById(withdrawalId);
        return new ResponseEntity<>(HttpStatus.OK);
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
