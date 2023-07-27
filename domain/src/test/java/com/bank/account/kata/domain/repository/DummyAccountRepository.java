package com.bank.account.kata.domain.repository;

import com.bank.account.kata.domain.model.account.Account;
import com.bank.account.kata.domain.port.secondary.repository.AccountRepository;
import java.util.Optional;
import java.util.UUID;

public class DummyAccountRepository implements AccountRepository {
    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public boolean existsWithId(UUID id) {
        return false;
    }

    @Override
    public Optional<Account> findById(UUID accountId) {
        return Optional.empty();
    }
}
