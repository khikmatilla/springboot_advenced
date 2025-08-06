package com.myproject.springboot_advenced.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Transaction;

public interface TransactionService {
    BaseResponse<JsonNode> checkPerformTransaction(CheckPerformTransactionRequest request);

    BaseResponse<Transaction> createTransaction(CreateTransactionRequest request);

    BaseResponse<Transaction> performTransaction(PerformTransactionRequest request);

    BaseResponse<JsonNode> cancelTransaction(CancelTransaction request);
}
