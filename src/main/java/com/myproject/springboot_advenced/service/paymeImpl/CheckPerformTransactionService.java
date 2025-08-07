package com.myproject.springboot_advenced.service.paymeImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.springboot_advenced.dto.CheckPerformTransactionRequest;
import com.myproject.springboot_advenced.dto.Method;
import com.myproject.springboot_advenced.dto.Response;
import com.myproject.springboot_advenced.service.PaymeService;
import com.myproject.springboot_advenced.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.myproject.springboot_advenced.dto.Error;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckPerformTransactionService implements PaymeService {

    private final ObjectMapper objectMapper;
    private final TransactionService transactionService;

    @Override
    public Response execute(JsonNode data) {
        CheckPerformTransactionRequest request = objectMapper.convertValue(data, CheckPerformTransactionRequest.class);

        log.info("CheckPerformTransactionRequest: {}", request);

        String orderId = request.getAccount().get("order_id");

        log.info("OrderId: {}", orderId);

        var body = transactionService.checkPerformTransaction(request);

        log.info("CheckPerformTransactionResponse: {}", body);

        if (body != null && Objects.equals(body.getMessage(), "SUCCESS")) {
            Map<String, Boolean> map = new HashMap<>();
            map.put("allow", true);
            return Response.builder()
                    .result(objectMapper.convertValue(map, JsonNode.class))
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
        return Method.CheckPerformTransaction;
    }
}
