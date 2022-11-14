package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class DepositService {

    @Autowired
   private DepositRepository depositRepository;

    public ResponseEntity<?> findAllDepositsForAccount(Long accountId){
        Iterable<Deposit> deposit = depositRepository.findDepositsForAccount(accountId);

        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }
    public ResponseEntity<?> findDepositById( Long depositId){

        return new ResponseEntity<>(depositRepository.findById(depositId),HttpStatus.OK);
    }
    public ResponseEntity<?> createDeposit( Long accountId,  Deposit deposit){
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
        deposit.setId(depositId);
        depositRepository.save(deposit);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> deleteDepositById( Long depositId){
       depositRepository.deleteById(depositId);
       return new ResponseEntity<>(HttpStatus.OK);
    }


}
