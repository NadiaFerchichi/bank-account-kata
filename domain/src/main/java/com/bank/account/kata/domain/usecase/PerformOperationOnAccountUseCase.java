package com.bank.account.kata.domain.usecase;

import com.bank.account.kata.domain.exception.NotFoundException;
import com.bank.account.kata.domain.factories.OperationFactory;
import com.bank.account.kata.domain.model.account.Account;
import com.bank.account.kata.domain.model.operation.Operation;
import com.bank.account.kata.domain.model.operation.OperationType;
import com.bank.account.kata.domain.port.secondary.repository.AccountRepository;
import com.bank.account.kata.domain.port.secondary.repository.OperationRepository;
import com.bank.account.kata.domain.port.secondary.transaction.UserTransaction;
import java.util.UUID;

public class PerformOperationOnAccountUseCase {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final UserTransaction userTransaction;

    public PerformOperationOnAccountUseCase(
            AccountRepository accountRepository,
            OperationRepository operationRepository,
            UserTransaction userTransaction) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.userTransaction = userTransaction;
    }

    public void handle(UUID accountId, OperationType operationType, Double amount) {
        Account account = retrieveAccount(accountId);
        Operation operation = OperationFactory.create(operationType, accountId, amount, account.getBalance());
        operation.perform(account);
        try {
            userTransaction.begin();
            accountRepository.save(account);
            operationRepository.save(operation);
            userTransaction.commit();
        } catch (Exception exception) {
            userTransaction.rollback();
        }
    }

    private Account retrieveAccount(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new NotFoundException("No account found with the provided id")
                );
    }
}
