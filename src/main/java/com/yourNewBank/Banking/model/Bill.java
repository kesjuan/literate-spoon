package com.yourNewBank.Banking.model;

import com.yourNewBank.enums.IStatus;

import javax.persistence.*;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private IStatus status;

    private String payee;

    private String nickName;

    @Column(name = "creation_date")
    private  String creationDate;

    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "recurring_date")
    private Integer recurringDate;

    @Column(name = "upcoming_payment_date")
    private String upcomingPaymentDate;
    @Column(name = "payment_amount")
    private Double paymentAmount;

    @JoinColumn(name = "account_id")
    @Column(name = "account_id")
    private Long accountId;

    public Bill() {
    }

    public IStatus getStatus() {
        return status;
    }

    public void setStatus(IStatus status) {
        this.status = status;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getRecurringDate() {
        return recurringDate;
    }

    public void setRecurringDate(Integer recurringDate) {
        this.recurringDate = recurringDate;
    }

    public String getUpcomingPaymentDate() {
        return upcomingPaymentDate;
    }

    public void setUpcomingPaymentDate(String upcomingPaymentDate) {
        this.upcomingPaymentDate = upcomingPaymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}