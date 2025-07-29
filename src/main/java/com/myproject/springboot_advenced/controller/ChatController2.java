package com.myproject.springboot_advenced.controller;


import com.myproject.springboot_advenced.dto.MessageDTO;
import com.myproject.springboot_advenced.service.ChatService2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController2 {

    private final ChatService2 chatService2;

    public ChatController2(ChatService2 chatService2) {
        this.chatService2 = chatService2;
    }

    @MessageMapping("/chat")
    public MessageDTO sendMessage(MessageDTO dto) {
        return chatService2.sendMessage(dto);
    }
}
