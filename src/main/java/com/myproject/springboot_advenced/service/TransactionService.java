package com.myproject.springboot_advenced.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Transaction;

import java.util.Map;

public interface TransactionService {
    BaseResponse<JsonNode> checkPerformTransaction(CheckPerformTransactionRequest data);

    BaseResponse<TransactionDTO> createTransaction(CreateTransactionRequest request);

    BaseResponse<TransactionDTO> performTransaction(PerformTransactionRequest request);

    BaseResponse<TransactionDTO> checkTransaction(CheckTransactionRequest request);

    BaseResponse<TransactionDTO> cancelTransaction(CancelTransactionRequest request);
}
