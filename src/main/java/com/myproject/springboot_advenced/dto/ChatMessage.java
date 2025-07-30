package com.myproject.springboot_advenced.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String sender;
    private String receiver;
    private String content;

}