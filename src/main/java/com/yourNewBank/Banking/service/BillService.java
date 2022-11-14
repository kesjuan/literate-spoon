package com.yourNewBank.Banking.service;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
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

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
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

    @Autowired
    private CustomerRepository customerRepository;



    public ResponseEntity<?> findAllBillsForThisAccount( Long accountId){
    // have to make query in repo to find bills for specific account id
            verifyAccount(accountId,"Error fetching Bills");
        return new ResponseEntity<>(billRepository.getBillsWithAccountId(accountId),HttpStatus.OK);
    }
    public ResponseEntity<?> findBillById( Long billId){
        verifyBill(billId,"Error fetching bill with id");
        return new ResponseEntity<>(billRepository.findById(billId),HttpStatus.OK);
    }

    public ResponseEntity<?> findBillByCustomer(Long customerId){
        verifyCustomer(customerId,"Error fetching bills");
        Iterable<Bill> bills =  billRepository.getBillsWithCustomerId(customerId);
        //Iterable<Bill> bills = billRepository.findAllById((Iterable<Long>) billIds);
        //have to make query in repo to find bills for specific customer Id
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }
    public ResponseEntity<?> createBillForThisAccount( Long accountId, Bill bill){
        verifyAccount(accountId,"Error creating bill: Account not found");
        bill.setAccountId(accountId);
        Date date = new Date();
        String stringDate = date.toInstant().atZone(ZonedDateTime.now().getZone()).toString();
        int endOfDate = stringDate.indexOf("T");
        String finalDate = (String) stringDate.subSequence(0,endOfDate);
        bill.setCreationDate(finalDate);
        bill.setRecurringDate(date.getDate());
        int startOfMonthIndex = finalDate.indexOf("-");
        bill.setUpcomingPaymentDate(setUpcomingPaymentDate(bill));

        // need to set upcomingPaymentDate
        //bill.setUpcomingPaymentDate();
        Customer customer = customerService.getCustomerByAccountIdNotForController(accountId);
        bill.setNickName(customer.getFirstName() + "'s "+ "bill from " + bill.getPayee());
        billRepository.save(bill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<?> updateBillById( Long billId, Bill bill){
        verifyBill(billId,"Bill id does not exist");
            bill.setId(billId);
            billRepository.save(bill);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> deleteBillById(Long billId){
        verifyBill(billId,"Bill id does not exist");
        billRepository.deleteById(billId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public String setUpcomingPaymentDate(Bill bill){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = bill.getRecurringDate();

        int realMonth = calendar.get(Calendar.MONTH) + 1;
        int month = realMonth + 1;
        int realDay = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-"+month+"-" + day);
        if (realDay > day ){
            month = realMonth+1;
            if (month > 12){
                month = 0;
            }
        }
        sdf.format(date);
        return sdf.format(date);
    }
    protected void verifyAccount(long accountId, String message)throws ResourceNotFoundException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
    protected void verifyBill(long billId, String message)throws ResourceNotFoundException {
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
    protected void verifyCustomer(long customerId, String message)throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()){// if pollid doesnt exist in the database then 404 not found status will be thrown
            // restExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException(),message);
            throw new ResourceNotFoundException(message);
        }            // ^^ custom class
    }
}
