package com.yourNewBank.Banking.repository;

import com.yourNewBank.Banking.model.Deposit;
import com.yourNewBank.Banking.model.Withdrawal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepository extends CrudRepository<Withdrawal, Long> {
    @Query(value = "select * from Withdrawal where payer_id = ?1",nativeQuery = true)
    public Iterable<Withdrawal> findWithdrawalsForAccount(Long accountId);
}