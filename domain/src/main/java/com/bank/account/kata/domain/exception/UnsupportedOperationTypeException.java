package com.bank.account.kata.domain.exception;

public class UnsupportedOperationTypeException
        extends RuntimeException {

    public UnsupportedOperationTypeException(
            String message
    ) {
        super(message);
    }
}
