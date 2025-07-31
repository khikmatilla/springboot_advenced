package com.myproject.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDto {

    private Long id;
    private String user1;
    private String user2;
    private List<MessageDto> messages;

    // Constructor, Getters, Setters
}
