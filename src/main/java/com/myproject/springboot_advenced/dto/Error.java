package com.myproject.springboot_advenced.dto;

import com.myproject.springboot_advenced.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private int code;
    private Message message;
    private String data;

    public static Error ORDER_NOT_FOUND_ERROR(String orderId) {
        return new Error(-31050, MessageUtils.ORDER_NOT_FOUND, orderId);
    }

    public static Error INCORRECT_AMOUNT_ERROR(String orderId) {
        return new Error(-31001, MessageUtils.INCORRECT_AMOUNT, orderId);
    }

    public static Error METHOD_NOT_FOUND_ERROR(String orderId) {
        return new Error(-32601, MessageUtils.METHOD_NOT_FOUND, orderId);
    }

    public static Error SYSTEM_ERROR(String orderId) {
        return new Error(-32400, MessageUtils.SYSTEM_ERROR, orderId);
    }

    public static Error TRANSACTION_NOT_FOUND_ERROR(String orderId) {
        return new Error(-31003, MessageUtils.TRANSACTION_NOT_FOUND, orderId);
    }
}
