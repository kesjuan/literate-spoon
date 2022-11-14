package com.yourNewBank.dto;

import com.yourNewBank.enums.AccountType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.yourNewBank.Banking.model.Account} entity
 */
public class AccountDto implements Serializable {
    private final Long id;
    private final AccountType type;
    private final String nickName;
    private final Integer rewards;
    private final Double balance;
    private final CustomerDto customer;

    public AccountDto(Long id, AccountType type, String nickName, Integer rewards, Double balance, CustomerDto customer) {
        this.id = id;
        this.type = type;
        this.nickName = nickName;
        this.rewards = rewards;
        this.balance = balance;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public AccountType getType() {
        return type;
    }

    public String getNickName() {
        return nickName;
    }

    public Integer getRewards() {
        return rewards;
    }

    public Double getBalance() {
        return balance;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto entity = (AccountDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.type, entity.type) &&
                Objects.equals(this.nickName, entity.nickName) &&
                Objects.equals(this.rewards, entity.rewards) &&
                Objects.equals(this.balance, entity.balance) &&
                Objects.equals(this.customer, entity.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, nickName, rewards, balance, customer);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "type = " + type + ", " +
                "nickName = " + nickName + ", " +
                "rewards = " + rewards + ", " +
                "balance = " + balance + ", " +
                "customer = " + customer + ")";
    }
}