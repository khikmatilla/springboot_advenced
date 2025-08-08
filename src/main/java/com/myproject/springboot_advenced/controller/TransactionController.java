package com.myproject.springboot_advenced.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Transaction;
import com.myproject.springboot_advenced.service.TransactionService;
import com.myproject.springboot_advenced.service.impl.PaymeResolverService;
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
    private final PaymeResolverService paymeResolverService;

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

                Response paymeResponse = paymeResolverService.resolve(request);

                if (paymeResponse.getResult() != null) {
                    return ResponseEntity.ok(paymeResponse);
                } else {
                    return ResponseEntity.badRequest().body(paymeResponse);
                }

            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @PostMapping("/chack-perform-transaction")
    public BaseResponse<JsonNode> chackPerformTransaction(@RequestBody CheckPerformTransactionRequest request) {
        return transactionService.checkPerformTransaction(request);
    }

    @PostMapping("/create-transaction")
    public BaseResponse<TransactionDTO> createTransaction(@RequestBody CreateTransactionRequest request) {
        log.info("REST createTransaction: {}", request);
        return transactionService.createTransaction(request);
    }

    @PostMapping("/perform-transaction")
    public BaseResponse<TransactionDTO> performTransaction(@RequestBody PerformTransactionRequest request) {
        log.info("REST performTransaction: {}", request);
        return transactionService.performTransaction(request);
    }

    @PostMapping("/check-transaction")
    public BaseResponse<TransactionDTO> checkTransaction(@RequestBody CheckTransactionRequest request) {
        log.info("REST checkTransaction: {}", request);
        return transactionService.checkTransaction(request);
    }

    @PostMapping("/cancel-transaction")
    public BaseResponse<TransactionDTO> cancelTransaction(@RequestBody CancelTransactionRequest request) {
        log.info("REST cancelTransaction: {}", request);
        return transactionService.cancelTransaction(request);
    }
}
