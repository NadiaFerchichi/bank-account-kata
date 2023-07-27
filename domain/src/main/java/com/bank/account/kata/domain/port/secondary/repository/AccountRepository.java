package com.bank.account.kata.domain.port.secondary.repository;

import com.bank.account.kata.domain.model.account.Account;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Account save(Account account);
    boolean existsWithId(UUID id);
    Optional<Account> findById(UUID accountId);
}
