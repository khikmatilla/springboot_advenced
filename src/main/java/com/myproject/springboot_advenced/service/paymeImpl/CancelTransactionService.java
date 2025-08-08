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
import com.myproject.springboot_advenced.dto.Error;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class CancelTransactionService implements PaymeService {

    private final ObjectMapper objectMapper;
    private final TransactionService transactionService;

    @Override
    public Response execute(JsonNode data) {

        CancelTransactionRequest cancelTransactionRequest = objectMapper.convertValue(data, CancelTransactionRequest.class);

        BaseResponse<TransactionDTO> response = transactionService.cancelTransaction(cancelTransactionRequest);

        if (Objects.equals(response.getMessage(), "TRANSACTION_NOT_FOUND")) {
            return Response.builder()
                    .error(Error.TRANSACTION_NOT_FOUND_ERROR(cancelTransactionRequest.getId()))
                    .build();
        }

        LocalDateTime cancelTime = response.getData().getCancelTime();
        long cancelTimeMilli = cancelTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (Objects.equals(response.getMessage(), "SUCCESS")) {
            CancelTransactionResponse cancelTransactionResponse = new CancelTransactionResponse(
                    response.getData().getTransactionId(),
                    cancelTimeMilli,
                    response.getData().getState()

            );
            return Response.builder()
                    .result(objectMapper.convertValue(cancelTransactionResponse, JsonNode.class))
                    .build();
        }

        return Response.builder()
                .error(Error.SYSTEM_ERROR(response.getData().getTransactionId()))
                .build();
    }

    @Override
    public Method getPaymeMethod() {
        return Method.CancelTransaction;
    }
}
