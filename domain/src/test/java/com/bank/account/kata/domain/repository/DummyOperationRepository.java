package com.bank.account.kata.domain.repository;

import com.bank.account.kata.domain.model.Page;
import com.bank.account.kata.domain.model.operation.Operation;
import com.bank.account.kata.domain.port.secondary.repository.OperationRepository;
import java.util.List;
import java.util.UUID;

public class DummyOperationRepository implements OperationRepository {
    @Override
    public Operation save(Operation operation) {
        return null;
    }

    @Override
    public List<Operation> findByAccountId(UUID accountId, Page page) {
        return null;
    }
}
