package com.myproject.springboot_advenced.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformTransactionResponse {
    private Long transaction;
    @JsonProperty("perform_time")
    private Long performTime;
    private int state;
}
