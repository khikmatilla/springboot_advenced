package com.myproject.springboot_advenced.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Transaction;

import java.util.Map;

public interface TransactionService {
    BaseResponse<JsonNode> checkPerformTransaction(Long amount, Map<String, String> account);

    BaseResponse<Transaction> createTransaction(CreateTransactionRequest request);

    BaseResponse<Transaction> performTransaction(PerformTransactionRequest request);

    BaseResponse<JsonNode> cancelTransaction(CancelTransaction request);
}
