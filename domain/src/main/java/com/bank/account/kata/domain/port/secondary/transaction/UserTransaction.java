package com.bank.account.kata.domain.port.secondary.transaction;

public interface UserTransaction {
    void begin();
    void commit();
    void rollback();
}
