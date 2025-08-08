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
public class CheckTransactionService implements PaymeService {

    private final ObjectMapper objectMapper;
    private final TransactionService transactionService;

    @Override
    public Response execute(JsonNode data) {

        CheckTransactionRequest checkTransactionRequest = objectMapper.convertValue(data, CheckTransactionRequest.class);
        log.info("CheckTransactionRequest: {}", checkTransactionRequest);

        BaseResponse<TransactionDTO> body = transactionService.checkTransaction(checkTransactionRequest);

        LocalDateTime createTime = body.getData().getCreatedTime();
        long createTimeMilli = createTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        LocalDateTime performTime = body.getData().getPerformTime();
        long performTimeMilli = performTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        LocalDateTime cancelTime = body.getData().getCancelTime();
        long cancelTimeMilli = cancelTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();



        if (body != null && Objects.equals(body.getMessage(), "SUCCESS") && body.getData() != null) {
            CheckTransactionResponse checkTransactionResponse = new CheckTransactionResponse(
                    createTimeMilli,
                    performTimeMilli,
                    cancelTimeMilli,
                    body.getData().getId(),
                    body.getData().getState().getCode(),
                    body.getData().getReason()
            );
            return Response.builder()
                    .result(objectMapper.convertValue(checkTransactionResponse, JsonNode.class))
                    .build();
        }

        if (body != null && Objects.equals(body.getMessage(), "TRANSACTION_NOT_FOUND")) {
            return Response.builder()
                    .error(Error.TRANSACTION_NOT_FOUND_ERROR(checkTransactionRequest.getId()))
                    .build();
        }

        return Response.builder()
                .error(Error.SYSTEM_ERROR(checkTransactionRequest.getId()))
                .build();
    }

    @Override
    public Method getPaymeMethod() {
        return Method.CheckTransaction;
    }
}
