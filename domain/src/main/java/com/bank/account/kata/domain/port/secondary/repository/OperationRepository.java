package com.bank.account.kata.domain.port.secondary.repository;

import com.bank.account.kata.domain.model.Page;
import com.bank.account.kata.domain.model.operation.Operation;
import java.util.List;
import java.util.UUID;

public interface OperationRepository {
    Operation save(Operation operation);
    List<Operation> findByAccountId(
            UUID accountId,
            Page page
    );
}
