package com.myproject.springboot_advenced.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckTransactionResponse {
    @JsonProperty("create_time")
    private Long createTime;
    @JsonProperty("perform_time")
    private Long performTime;
    @JsonProperty("cancel_time")
    private Long cancelTime;
    private Long transaction;
    private Integer state;
    private Integer reason;
}