package com.myproject.uz.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Long receiverId;
    private String receiverUsername;
    private String content;
}
