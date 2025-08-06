package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {
    private Long orderId;
    private String id;
    private long time;
    private long amount;
    Map<String, String> account;
}
