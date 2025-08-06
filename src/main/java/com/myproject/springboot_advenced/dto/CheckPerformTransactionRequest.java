package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckPerformTransactionRequest {
    private Long amount;
    Map<String, String> account;
}
