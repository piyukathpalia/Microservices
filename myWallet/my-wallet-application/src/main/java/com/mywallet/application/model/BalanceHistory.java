package com.mywallet.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "balance_history")
public class BalanceHistory {

    @Transient
    public static final String SEQUENCE_NAME = "balance_history_sequence";

    @Id
    private long id;
    private long customerId;
    private double amount;
    private double balanceAfterTransaction;
    private String transactionType;
    private Date transactionDate;
    private String category;

    public BalanceHistory(long id, long customerId, double amount, double balanceAfterTransaction, String transactionType, Date transactionDate, String category) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.category = category;
    }

    public BalanceHistory() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
