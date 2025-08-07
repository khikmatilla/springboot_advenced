package com.myproject.springboot_advenced.utils;

import com.myproject.springboot_advenced.dto.Message;
import lombok.Data;

@Data
public class MessageUtils {

    public static final Message ORDER_NOT_FOUND = new Message(
            "Заказь не найден",
            "Buyurtma topilmadi",
            "Order not found"
    );

    public static final Message TRANSACTION_NOT_FOUND = new Message(
            "Транзакция не найден",
            "Transaksiya topilmadi",
            "Transaction not found"
    );

    public static final Message CAN_NOT_BE_PERFORMED = new Message(
            "Невозможно выполнить данную операцию",
            "Ushbu operatsiyani bajarish mumkin emas",
            "This operation cannot be performed"
    );

    public static final Message ORDER_COMPLETED = new Message(
            "Заказ выполнен",
            "Buyurtma bajarilindi",
            "The order is completed"
    );

    public static final Message INCORRECT_AMOUNT = new Message(
            "Неверная сумма",
            "No'to'g'ri summa",
            "Incorrect amount"
    );

    public static final Message METHOD_NOT_FOUND = new Message(
            "Запрашиваемый метод не найден",
            "Method topilmadi",
            "Method not found"
    );

    public static final Message SYSTEM_ERROR = new Message(
            "Системная (внутренняя ошибка)",
            "Tizim xatoligi",
            "System error"
    );
}
