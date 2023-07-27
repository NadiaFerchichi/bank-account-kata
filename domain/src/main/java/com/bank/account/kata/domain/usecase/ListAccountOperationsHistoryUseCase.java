package com.bank.account.kata.domain.usecase;

import com.bank.account.kata.domain.exception.NotFoundException;
import com.bank.account.kata.domain.model.Page;
import com.bank.account.kata.domain.model.operation.Operation;
import com.bank.account.kata.domain.port.secondary.repository.AccountRepository;
import com.bank.account.kata.domain.port.secondary.repository.OperationRepository;
import java.util.List;
import java.util.UUID;

public class ListAccountOperationsHistoryUseCase {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public ListAccountOperationsHistoryUseCase(
            AccountRepository accountRepository,
            OperationRepository operationRepository
    ) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    public List<Operation> byAccountId(UUID accountId, Page page) {
        checkThatAccountIdExists(accountId);
        return operationRepository.findByAccountId(accountId, page);
    }

    private void checkThatAccountIdExists(UUID accountId) {
        if(accountId != null && !accountRepository.existsWithId(accountId)) {
            throw new NotFoundException("No account found with the provided id");
        }
    }
}
