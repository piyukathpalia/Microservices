package com.mywallet.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Positive;

@Document(collection = "customer_wallet")
public class CustomerWallet {
    @Id
    private long id;
    @Positive(message = "amount should be positive value")
    private double balance;

    @Transient
    private String authHeader;

    public CustomerWallet(long id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public CustomerWallet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }

}
