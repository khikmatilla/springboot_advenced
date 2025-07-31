package com.myproject.uz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private Long chatId;
    private String from;
    private String to;
    private String content;
}
