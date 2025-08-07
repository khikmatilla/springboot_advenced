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


import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateTransactionService implements PaymeService {

    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;



    @Override
    public Response execute(JsonNode data) {
        CreateTransactionRequest createTransactionRequest = objectMapper.convertValue(data, CreateTransactionRequest.class);

        log.info("CreateTransactionRequest: {}", createTransactionRequest);

        String orderId = createTransactionRequest.getAccount().get("order_id");

        BaseResponse<TransactionDTO> body = transactionService.createTransaction(createTransactionRequest);


        if (body != null && Objects.equals(body.getMessage(), "SUCCESS") && body.getData() != null) {
            var createTransactionResponse = new CreateTransactionResponse(
                    body.getData().getTime(),
                    body.getData().getId(),
                    body.getData().getState().getCode()
            );
            return Response.builder()
                    .result(objectMapper.convertValue(createTransactionResponse, JsonNode.class))
                    .build();
        }

        if (body != null && Objects.equals(body.getMessage(), "ORDER_NOT_FOUND")) {
            return Response.builder()
                    .error(Error.ORDER_NOT_FOUND_ERROR(orderId))
                    .build();
        }

        if (body != null && Objects.equals(body.getMessage(), "INCORRECT_AMOUNT")) {
            return Response.builder()
                    .error(Error.INCORRECT_AMOUNT_ERROR(orderId))
                    .build();
        }

        return Response.builder()
                .error(Error.SYSTEM_ERROR(orderId))
                .build();
    }

    @Override
    public Method getPaymeMethod() {
        return Method.CreateTransaction;
    }
}
