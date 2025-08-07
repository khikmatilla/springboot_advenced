package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long id;

    private String transactionId;

    private Long amount;

    private TransactionState state;

    private Long orderId;

    private LocalDateTime createTime;

    private LocalDateTime performTime;

    private LocalDateTime cancelTime;

    private Long time;
}
