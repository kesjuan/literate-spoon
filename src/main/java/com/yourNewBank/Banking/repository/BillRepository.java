package com.yourNewBank.Banking.repository;

import com.yourNewBank.Banking.model.Bill;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long>{
// have to make query in repo to find bills for specific account id
    @Query(value = "select * from Bill where account_id = ?1",nativeQuery = true)
    public Iterable<Bill> getBillsWithAccountId(Long accountId);

    //have to make query in repo to find bills for specific customer Id
    @Query(value = "select",nativeQuery = true)
    public Iterable<Bill> getBillsWithCustomerId(Long customerId);
}