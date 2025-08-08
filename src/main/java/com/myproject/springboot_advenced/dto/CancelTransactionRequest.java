package com.myproject.springboot_advenced.dto;

import lombok.Data;

@Data
public class CancelTransactionRequest {
    private String id;
    private Integer reason;
}
