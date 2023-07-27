package com.bank.account.kata.domain.model.operation;

import com.bank.account.kata.domain.model.account.Account;
import java.time.OffsetDateTime;
import java.util.UUID;

public class WithdrawalOperation extends Operation {

    public WithdrawalOperation(UUID accountId, Double amount, Double balance, OffsetDateTime date) {
        super(accountId, OperationType.WITHDRAWAL, amount, balance, date);
    }

    @Override
    public void perform(Account account) {
        account.withdraw(amount);
        this.balance = account.getBalance();
    }
}
