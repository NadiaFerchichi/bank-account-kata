package com.bank.account.kata.domain.exception;


public class NotPossibleOperation
        extends RuntimeException {

    public NotPossibleOperation(
            String message
    ) {
        super(message);
    }
}
