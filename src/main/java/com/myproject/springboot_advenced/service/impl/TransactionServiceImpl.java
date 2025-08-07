package com.myproject.springboot_advenced.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.*;
import com.myproject.springboot_advenced.entity.Order;
import com.myproject.springboot_advenced.entity.Transaction;
import com.myproject.springboot_advenced.dto.TransactionState;
import com.myproject.springboot_advenced.mapper.TransactionMapper;
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
    private final TransactionMapper transactionMapper;

    @Override
    public BaseResponse<JsonNode> checkPerformTransaction(CheckPerformTransactionRequest request) {

        long orderId;
        if (!request.getAccount().containsKey("phone")) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }
        orderId = Long.parseLong(request.getAccount().get("phone"));
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }

        if (!Objects.equals(order.getTotalAmount(), request.getAmount())) {
            return new BaseResponse<>(false, INCORRECT_AMOUNT.name());
        }

        return new BaseResponse<>(true, PaymentMessage.SUCCESS.name());
    }

    @Override
    public BaseResponse<TransactionDTO> createTransaction(CreateTransactionRequest request) {
        log.info("createTransaction: {}", request);

        long phoneNum;
        if (!request.getAccount().containsKey("phone")) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }

        phoneNum = Long.parseLong((request.getAccount().get("phone")));

        var order = orderRepository.findById(phoneNum).orElse(null);

        if (order == null) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }

        if (!Objects.equals(order.getTotalAmount(), request.getAmount())) {
            return new BaseResponse<>(false, INCORRECT_AMOUNT.name());
        }
        Transaction transaction = Transaction.builder()
                .transactionId(request.getId())
                .amount(request.getAmount())
                .state(TransactionState.CREATED)
                .createdTime(LocalDateTime.now())
                .order(order)
                .build();

        log.info("transaction: {}", transaction);

        return new BaseResponse<>(true, SUCCESS.name(), transactionMapper.toDto(transaction));
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
