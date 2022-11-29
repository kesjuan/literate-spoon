package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.Banking.handler.ResponseHandler;
import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.DepositRepository;
import com.yourNewBank.enums.IMedium;
import com.yourNewBank.enums.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(DepositService.class);

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


    //not a postman method
    //p2p method
//    public void p2pTransaction(Long accountId, Deposit deposit){
//        if (deposit.getType() == TransactionType.P2P){
//            if(deposit.getAmount() > 0) {
//                Double balanceOfPayee = deposit.getAmount() + accountRepository.findById(deposit.getPayeeId()).get().getBalance();
//                Double balanceOfPayer = accountRepository.findById(accountId).get().getBalance() - deposit.getAmount();
//                accountRepository.findById(deposit.getPayeeId()).get().setBalance(balanceOfPayee);
//                accountRepository.findById(accountId).get().setBalance(balanceOfPayer);
//            }
//        }
//    }
    public ResponseEntity<?> createDeposit( Long accountId,  Deposit deposit){
        try{
            verifyAccount(accountId,"Error creating deposit: Account not found");
            Date date = new Date();
            String stringDate = date.toInstant().atZone(ZonedDateTime.now().getZone()).toString();
            int endOfDate = stringDate.indexOf("T");
            String finalDate = (String) stringDate.subSequence(0,endOfDate);
            //deposit.setPayeeId(accountId);
            deposit.setTransactionDate(finalDate);
           // Optional<Account> account = accountRepository.findById(accountId);
            if (deposit.getMedium() == IMedium.Balance){
                if (deposit.getType() == TransactionType.P2P){
                    if(deposit.getAmount() > 0) {
                        Long payeeId = deposit.getPayeeId();
                        Double balanceOfPayee = accountRepository.findById(payeeId).get().getBalance() + deposit.getAmount();
                        Double balanceOfPayer = accountRepository.findById(accountId).get().getBalance() - deposit.getAmount();
                        accountRepository.findById(payeeId).get().setBalance(balanceOfPayee);
                        accountRepository.findById(accountId).get().setBalance(balanceOfPayer);
                        log.info("p2p if statement");
                    }
                }else if(deposit.getAmount() > 0){
                    Double balance = accountRepository.findById(accountId).get().getBalance() + deposit.getAmount();
                    accountRepository.findById(accountId).get().setBalance(balance);
                    log.info("deposit if statement");
                }
            }


            if (deposit.getMedium() == IMedium.Rewards) {
                if (deposit.getAmount() > 0) {
                    accountRepository.findById(accountId).get().setRewards(accountRepository.findById(accountId).get().getRewards() + deposit.getAmount());
                }
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
            return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, "Accepted deposit modification");
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    public ResponseEntity<?> deleteDepositById( Long depositId){
        try{
            verifyDeposit(depositId,"Deposit id does not exist");
            depositRepository.deleteById(depositId);
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
