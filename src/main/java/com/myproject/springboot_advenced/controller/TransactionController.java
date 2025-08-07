package com.myproject.springboot_advenced.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Transaction;
import com.myproject.springboot_advenced.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/payme")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/pay")
    public ResponseEntity<Response> pay(
            @RequestBody Request request,
            @RequestHeader(value = "authorization", required = false) String authHeader) {
        if (authHeader == null || authHeader.startsWith("Basic ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            try {
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(decodedBytes);

//                String[] userDetails = credentials.split(":", 2);
//
//                String username = userDetails[0];
//                String password = userDetails[1];
                if (request.getMethod().name().equals("CheckPerformTransaction")) {
                    BaseResponse<JsonNode> body = transactionService.checkPerformTransaction(
                            request.getParams().getAmount(), request.getParams().getAccount());
                    if (body != null && Objects.equals(body.getMessage(), "SUCCESS")) {
                        return ResponseEntity.ok().body(new Response(Map.of("allow", true)));
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    @PostMapping("/chack-perform-transaction")
    public BaseResponse<JsonNode> chackPerformTransaction(@RequestBody Long amount, @RequestBody Map<String, String> account) {
        return transactionService.checkPerformTransaction(amount, account);
    }

    @PostMapping("/create-transaction")
    public BaseResponse<Transaction> createTransaction(@RequestBody CreateTransactionRequest request) {
        log.info("REST createTransaction: {}", request);
        return transactionService.createTransaction(request);
    }

    @PostMapping("/perform-transaction")
    public BaseResponse<Transaction> performTransaction(@RequestBody PerformTransactionRequest request) {
        log.info("REST performTransaction: {}", request);
        return transactionService.performTransaction(request);
    }

    @PostMapping("/cancel-transaction")
    public BaseResponse<JsonNode> cancelTransaction(@RequestBody CancelTransaction request) {
        log.info("REST cancelTransaction: {}", request);
        return transactionService.cancelTransaction(request);
    }
}
