package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CancelTransactionResponse {
    private String transactionId;
    private long cancel_time;
    private TransactionState state;
}
