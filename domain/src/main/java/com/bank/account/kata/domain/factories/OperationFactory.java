package com.bank.account.kata.domain.factories;

import com.bank.account.kata.domain.exception.UnsupportedOperationTypeException;
import com.bank.account.kata.domain.model.operation.DepositOperation;
import com.bank.account.kata.domain.model.operation.Operation;
import com.bank.account.kata.domain.model.operation.OperationType;
import com.bank.account.kata.domain.model.operation.WithdrawalOperation;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OperationFactory {

    public static Operation create(OperationType operationType, UUID accountId, Double amount, Double balance) {
        OffsetDateTime date = OffsetDateTime.now();
        return switch (operationType) {
            case DEPOSIT -> new DepositOperation(accountId, amount, balance, date);
            case WITHDRAWAL -> new WithdrawalOperation(accountId, amount, balance, date);
            default -> throw new UnsupportedOperationTypeException("Unsupported operation type.");
        };
    }
}
