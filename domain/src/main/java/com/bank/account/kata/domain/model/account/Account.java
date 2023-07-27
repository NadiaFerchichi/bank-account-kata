package com.bank.account.kata.domain.model.account;

import com.bank.account.kata.domain.exception.NotPossibleOperation;
import java.util.UUID;

public class Account {
    private UUID id;
    private Double balance;

    public Account(UUID id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    public void deposit(Double amount) {
        this.balance += amount;
    }

    public void withdraw(Double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new NotPossibleOperation("Insufficient funds.");
        }
    }

    public UUID getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }
}
