package com.myproject.springboot_advenced.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Transaction;
import com.myproject.springboot_advenced.service.TransactionService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/payme")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/pay")
    public ResponseEntity<Response> pay(
            @RequestBody Request request,
            @RequestHeader(value = "authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }else {
            try {
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(decodedBytes);

                String[] userDetails = credentials.split(":", 2);

                String username = userDetails[0];
                String password = userDetails[1];
                if (applicationProperties.getPayme().getUsername().equals(username)
                        && applicationProperties.getPayme().getPassword().equals(password)
                ) {
                    Response paymeResponse = paymeResolverService.resolve(request);

                    if (paymeResponse.getResult() != null) {
                        return ResponseEntity.ok(paymeResponse);
                    } else {
                        return ResponseEntity.badRequest().body(paymeResponse);
                    }

                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
                }
            }catch (IllegalArgumentException e) {
            throw  new RuntimeException(e.getMessage());
            }
        }
    }

    @PostMapping("/chack-perform-transaction")
    public BaseResponse<JsonNode> chackPerformTransaction(@RequestBody CheckPerformTransactionRequest request) {
        return transactionService.checkPerformTransaction(request);
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
