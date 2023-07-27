package com.bank.account.kata.domain.exception;

public class InvalidDataException
        extends RuntimeException {

    public InvalidDataException(
            String message
    ) {
        super(message);
    }
}
