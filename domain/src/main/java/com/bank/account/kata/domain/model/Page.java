package com.bank.account.kata.domain.model;

import java.util.Optional;

public class Page {
    private Integer number;
    private Integer size;

    public Page(Integer number, Integer size) {
        this.number = number;
        this.size = size;
    }
}
