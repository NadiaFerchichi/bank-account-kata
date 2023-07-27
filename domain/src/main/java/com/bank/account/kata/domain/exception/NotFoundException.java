package com.bank.account.kata.domain.exception;

public class NotFoundException
    extends RuntimeException {

    public NotFoundException(
        String message
    ) {
        super(message);
    }
}
