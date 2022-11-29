package com.yourNewBank.Banking.model;

import com.yourNewBank.enums.AccountType;

import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
@Enumerated(EnumType.STRING)
    private AccountType type;
    //private enum type{Savings, Checking, Credit}

    @Column(name = "nickname")
//    @JsonDeserialize(using = NickNameCreator.class)
    // String value with customers name and type of account
    //ex. Leons Savings Account
    private String nickName;
    //

    private Double rewards;

    private Double balance;

    //@ManyToOne
    @JoinColumn(name = "customer_id")
    @Column(name = "customer_id")
    private Long customerId;

    public Account() {
    }




//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Double getRewards() {
        return rewards;
    }

    public void setRewards(Double rewards) {
        this.rewards = rewards;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    //@JsonIgnore
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

//    public void depositToBalance(double depositAmount){
//            setBalance(balance + depositAmount);
//    }
//    public void depositToRewards(double depositAmount){
//            setRewards( rewards+ depositAmount);
//
//    }
//    public void withdrawalBalance(double withdrawalAmount){
//            setBalance(balance - withdrawalAmount);
//    }
//    public void withdrawalRewards(double withdrawalAmount){
//            setRewards( rewards -  withdrawalAmount);
//
//    }


}
