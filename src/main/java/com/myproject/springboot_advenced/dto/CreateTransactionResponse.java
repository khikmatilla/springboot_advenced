package com.myproject.springboot_advenced.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CreateTransactionResponse {
    @JsonProperty("createTime")
    private long createTime;
    private Long transaction;
    private int state;
}
