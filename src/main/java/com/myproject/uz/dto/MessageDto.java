package com.myproject.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {
    private Long id;
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime sentAt;

    // Constructor, Getters, Setters
}
