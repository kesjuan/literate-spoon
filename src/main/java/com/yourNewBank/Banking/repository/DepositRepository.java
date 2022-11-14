package com.yourNewBank.Banking.repository;

import com.yourNewBank.Banking.model.Deposit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long> {
    @Query(value = "select * from Deposit where payee_id = ?1",nativeQuery = true)
    public Iterable<Deposit> findDepositsForAccount(Long accountId);
}