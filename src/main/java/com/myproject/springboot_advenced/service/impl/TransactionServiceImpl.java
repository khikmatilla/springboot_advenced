package com.myproject.springboot_advenced.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Transaction;
import com.myproject.springboot_advenced.dto.TransactionState;
import com.myproject.springboot_advenced.repository.OrderRepository;
import com.myproject.springboot_advenced.repository.TransactionRepository;
import com.myproject.springboot_advenced.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static com.myproject.springboot_advenced.dto.PaymentMessage.*;
import static com.myproject.springboot_advenced.dto.TransactionState.CANCELED1;
import static com.myproject.springboot_advenced.dto.TransactionState.SUCCESS;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public BaseResponse<JsonNode> checkPerformTransaction(Long amount, Map<String, String> account) {

        String phoneNum;
        if (!account.containsKey("phone")) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }
        phoneNum = (account.get("phone"));
        var order = orderRepository.findByPhoneNumber(phoneNum).orElse(null);

        if (order == null) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }

        if (!Objects.equals(order.getTotalAmount(), amount)) {
            return new BaseResponse<>(false, INCORRECT_AMOUNT.name());
        }

        return new BaseResponse<>(true, PaymentMessage.SUCCESS.name());
    }

    @Override
    public BaseResponse<Transaction> createTransaction(CreateTransactionRequest request) {

        var orderId = orderRepository.findById(request.getOrderId()).orElse(null);
        Transaction transaction = Transaction.builder()
                .transactionId(request.getId())
                .amount(request.getAmount())
                .state(TransactionState.CREATED)
                .createdTime(LocalDateTime.now())
                .time(request.getTime())
                .order(orderId)
                .build();

        var savedTransaction = transactionRepository.save(transaction);
        log.info("transaction: {}", transaction);
        return new BaseResponse<>(true, PaymentMessage.SUCCESS.name(), savedTransaction);
    }

    @Override
    public BaseResponse<Transaction> performTransaction(PerformTransactionRequest request) {
        var transaction = transactionRepository.findByTransactionId(request.getId()).orElse(null);
        if (transaction == null) {
            return new BaseResponse<>(false, TRANSACTION_NOT_FOUND.name());
        }
        if (Objects.equals(transaction.getState(), TransactionState.CREATED)) {
            var order = orderRepository.findById(transaction.getOrder().getId()).orElse(null);
            assert order != null;
            orderRepository.save(order);

            transaction.setPerformTime(LocalDateTime.now());
            transaction.setState(SUCCESS);
            var createdTransaction = transactionRepository.save(transaction);

            return new BaseResponse<>(true, SUCCESS.name(), createdTransaction);
        }
        return new BaseResponse<>(false, TRANSACTION_NOT_FOUND.name());
    }

    @Override
    public BaseResponse<JsonNode> cancelTransaction(CancelTransaction request) {
        log.info("cancelTransaction: {}", request);
        var transaction = transactionRepository.findById(request.getTransactionId()).orElse(null);
        Transaction transaction1 = Transaction.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .createdTime(LocalDateTime.now())
                .order(transaction.getOrder())
                .state(CANCELED1)
                .cancelTime(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction1);
        return new BaseResponse<>(false, SUCCESS.name());
    }
}
