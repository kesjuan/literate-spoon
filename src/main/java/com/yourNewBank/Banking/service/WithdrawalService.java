package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.model.Withdrawal;
import com.yourNewBank.Banking.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    public ResponseEntity<?> findAllWithdrawalsForAccount(Long accountId){
        Iterable<Withdrawal> withdrawals = withdrawalRepository.findWithdrawalsForAccount(accountId);

        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
    public ResponseEntity<?> findWithdrawalById( Long withdrawalId){

        return new ResponseEntity<>(withdrawalRepository.findById(withdrawalId),HttpStatus.OK);
    }
    public ResponseEntity<?> createWithdrawal( Long accountId,  Withdrawal withdrawal){
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
        withdrawal.setId(withdrawalId);
        withdrawalRepository.save(withdrawal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> deleteWithdrawalById( Long withdrawalId){
        withdrawalRepository.deleteById(withdrawalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
