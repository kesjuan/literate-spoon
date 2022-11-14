package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.model.Account;
import com.yourNewBank.Banking.model.Bill;
import com.yourNewBank.Banking.model.Customer;
import com.yourNewBank.Banking.repository.AccountRepository;
import com.yourNewBank.Banking.repository.BillRepository;
import com.yourNewBank.Banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

//    public String date(){
//        Date date = new Date();
//        String dateString = date.toInstant().toString();
//        int endOfDate = dateString.indexOf("T");
//       char[] oldDateChar = dateString.toCharArray();
//       char[] newDateChar = new char[endOfDate -1];
//        for (int i = 0; i < oldDateChar.length; i++) {
//            if (i < endOfDate){
//               newDateChar[i] = oldDateChar[i];
//            }
//            String realDate = Arrays.toString(newDateChar);
//        }
//    }

    public ResponseEntity<?> findAllBillsForThisAccount( Long accountId){
    // have to make query in repo to find bills for specific account id

        return new ResponseEntity<>(billRepository.getBillsWithAccountId(accountId),HttpStatus.OK);
    }
    public ResponseEntity<?> findBillById( Long billId){
        return new ResponseEntity<>(billRepository.findById(billId),HttpStatus.OK);
    }

    public ResponseEntity<?> findBillByCustomer(Long customerId){
        //have to make query in repo to find bills for specific customer Id
        return null;
    }
    public ResponseEntity<?> createBillForThisAccount( Long accountId, Bill bill){
        bill.setAccountId(accountId);
        Date date = new Date();
        String stringDate = date.toInstant().atZone(ZonedDateTime.now().getZone()).toString();
        int endOfDate = stringDate.indexOf("T");
        String finalDate = (String) stringDate.subSequence(0,endOfDate);
        bill.setCreationDate(finalDate);
        bill.setRecurringDate(date.getDate());
        int startOfMonthIndex = finalDate.indexOf("-");


        // need to set upcomingPaymentDate
        //bill.setUpcomingPaymentDate();
        Customer customer = customerService.getCustomerByAccountIdNotForController(accountId);
        bill.setNickName(customer.getFirstName() + "'s "+ "bill from " + bill.getPayee());
        billRepository.save(bill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<?> updateBillById( Long billId, Bill bill){
            bill.setId(billId);
            billRepository.save(bill);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> deleteBillById(Long billId){
        billRepository.deleteById(billId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
