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

        String orderId;
        if (!request.getAccount().containsKey("phone")) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }
        orderId = request.getAccount().get("phone");
        Order order = orderRepository.findByContactAndStatus(orderId, OrderStatus.WAITING_FOR_PAYMENT).orElse(null);

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

        String phoneNum;
        if (!request.getAccount().containsKey("phone")) {
            return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
        }

        phoneNum = request.getAccount().get("phone");

        var order = orderRepository.findByContactAndStatus(phoneNum, OrderStatus.WAITING_FOR_PAYMENT).orElse(null);

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
                .time(request.getTime())
                .createdTime(LocalDateTime.now())
                .order(order)
                .build();

        log.info("transaction: {}", transaction);
        transaction = transactionRepository.save(transaction);

        return new BaseResponse<>(true, SUCCESS.name(), transactionMapper.toDto(transaction));
    }

    @Override
    public BaseResponse<TransactionDTO> performTransaction(PerformTransactionRequest request) {
        log.info("performTransaction: {}", request);
        var transaction = transactionRepository.findByTransactionId(request.getId()).orElse(null);
        if (transaction == null) {
            return new BaseResponse<>(false, TRANSACTION_NOT_FOUND.name());
        }
        if (Objects.equals(transaction.getState(), TransactionState.CREATED)) {
            var order = orderRepository.findById(transaction.getOrder().getId()).orElse(null);
            if (order == null) {
                return new BaseResponse<>(false, ORDER_NOT_FOUND.name());
            }
            order.setStatus(OrderStatus.FINISHED);
            orderRepository.save(order);

            transaction.setPerformTime(LocalDateTime.now());
            transaction.setState(SUCCESS);
            transaction = transactionRepository.save(transaction);

            return new BaseResponse<>(true, SUCCESS.name(), transactionMapper.toDto(transaction));
        }
        if (Objects.equals(transaction.getState(), TransactionState.SUCCESS)) {
            return new BaseResponse<>(true, SUCCESS.name(), transactionMapper.toDto(transaction));
        }

        return new BaseResponse<>(false, TRANSACTION_NOT_FOUND.name());
    }

    @Override
    public BaseResponse<TransactionDTO> checkTransaction(CheckTransactionRequest request) {
        log.info("checkTransaction: {}", request);

        var transaction = transactionRepository.findByTransactionId(request.getId()).orElse(null);
        if (transaction == null) {
            return new BaseResponse<>(false, TRANSACTION_NOT_FOUND.name());
        }

        return new BaseResponse<>(true, SUCCESS.name(), transactionMapper.toDto(transaction));

    }

    @Override
    public BaseResponse<TransactionDTO> cancelTransaction(CancelTransactionRequest request) {
        log.info("cancelTransaction: {}", request);
        var transaction = transactionRepository.findByTransactionId(request.getId()).orElse(null);
        if (transaction == null) {
            return new BaseResponse<>(false, TRANSACTION_NOT_FOUND.name());
        }
        transaction.setCancelTime(LocalDateTime.now());
        transaction.setState(CANCELED1);
        transaction.setReason(request.getReason());
        transactionRepository.save(transaction);
        return new BaseResponse<>(true,SUCCESS.name(), transactionMapper.toDto(transaction));
    }
}
