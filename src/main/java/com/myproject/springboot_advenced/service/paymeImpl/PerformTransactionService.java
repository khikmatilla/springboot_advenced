package com.myproject.springboot_advenced.service.paymeImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.dto.Error;
import com.myproject.springboot_advenced.service.PaymeService;
import com.myproject.springboot_advenced.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformTransactionService implements PaymeService {

    private final ObjectMapper objectMapper;
    private final TransactionService transactionService;

    @Override
    public Response execute(JsonNode data) {

        PerformTransactionRequest performTransactionRequest = objectMapper.convertValue(data, PerformTransactionRequest.class);
        log.info("PerformTransactionService: {}", performTransactionRequest);

        BaseResponse<TransactionDTO> body = transactionService.performTransaction(performTransactionRequest);

        LocalDateTime performTime = body.getData().getPerformTime();
        long performTimeMilli = performTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (body != null && Objects.equals(body.getMessage(), "SUCCESS") && body.getData() != null) {
            var performTransactionResponse = new PerformTransactionResponse(
                    body.getData().getId(),
                    performTimeMilli,
                    body.getData().getState().getCode()
            );
            return Response.builder()
                    .result(objectMapper.convertValue(performTransactionResponse, JsonNode.class))
                    .build();
        }

        if (body != null && Objects.equals(body.getMessage(), "TRANSACTION_NOT_FOUND")) {
            return Response.builder()
                    .error(Error.TRANSACTION_NOT_FOUND_ERROR(performTransactionRequest.getId()))
                    .build();
        }

        if (body != null && Objects.equals(body.getMessage(), "ORDER_NOT_FOUND")) {
            return Response.builder()
                    .error(Error.ORDER_NOT_FOUND_ERROR(performTransactionRequest.getId()))
                    .build();
        }

        return Response.builder()
                .error(Error.SYSTEM_ERROR(performTransactionRequest.getId()))
                .build();

    }

    @Override
    public Method getPaymeMethod() {
        return Method.PerformTransaction;
    }
}
