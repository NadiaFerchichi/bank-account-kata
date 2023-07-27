package com.bank.account.kata.domain.model.operation;

import com.bank.account.kata.domain.exception.InvalidDataException;
import com.bank.account.kata.domain.model.account.Account;
import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class Operation {
    protected UUID accountId;
    protected OperationType operationType;
    protected Double amount;
    protected Double balance;
    protected OffsetDateTime date;

    public Operation(UUID accountId, OperationType operationType, Double amount, Double balance, OffsetDateTime date) {
        this.accountId = accountId;
        this.operationType = operationType;
        setAmount(amount);
        this.balance = balance;
        this.date = date;
    }

    private void setAmount(Double amount) {
        if(amount <= 0) {
            throw new InvalidDataException("The amount should be positive.");
        }
        this.amount = amount;
    }

    public UUID getAccountId() {
        return this.accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getBalance() {
        return balance;
    }

    public abstract void perform(Account account);
}
