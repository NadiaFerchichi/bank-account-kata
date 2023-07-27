package com.bank.account.kata.domain.model.operation;

import com.bank.account.kata.domain.model.account.Account;
import java.time.OffsetDateTime;
import java.util.UUID;

public class DepositOperation extends Operation {

    public DepositOperation(UUID accountId, Double amount, Double balance, OffsetDateTime date) {
        super(accountId, OperationType.DEPOSIT, amount, balance, date);
    }

    @Override
    public void perform(Account account) {
        account.deposit(amount);
        this.balance = account.getBalance();
    }
}
