package com.yourNewBank.Banking.model;

import com.yourNewBank.enums.IMedium;
import com.yourNewBank.enums.IStatus;
import com.yourNewBank.enums.TransactionType;

import javax.persistence.*;

@Entity
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id", nullable = false)
    private Long id;
@Enumerated(EnumType.STRING)
    private TransactionType type;

@Column(name = "transaction_date")
    private String transactionDate;

    @Enumerated(EnumType.STRING)
    private IStatus status;

    @JoinColumn(name = "account_id")
    @Column(name = "payee_id")
    private Long payeeId;

    @Enumerated(EnumType.STRING)
    private IMedium medium;

    private Double amount;

    private String description;

    public Deposit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public IStatus getStatus() {
        return status;
    }

    public void setStatus(IStatus status) {
        this.status = status;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public IMedium getMedium() {
        return medium;
    }

    public void setMedium(IMedium medium) {
        this.medium = medium;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", type=" + type +
                ", transactionDate='" + transactionDate + '\'' +
                ", status=" + status +
                ", payeeId=" + payeeId +
                ", medium=" + medium +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}