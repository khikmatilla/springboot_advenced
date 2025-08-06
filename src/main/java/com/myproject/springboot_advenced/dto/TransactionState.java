package com.myproject.springboot_advenced.dto;

import lombok.Getter;

@Getter
public enum TransactionState {

    CREATED(1), SUCCESS(2), CANCELED1(-1), CANCELED2(-2);

    private final int code;

    TransactionState(int code) {
        this.code = code;
    }
}
