package com.myproject.uz.controller;

import com.myproject.uz.dto.ChatMessageDto;
import com.myproject.uz.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    @Autowired
    ChatService chatService;

    @MessageMapping("/chat.send")
    public void send(ChatMessageDto messageDto) {
        chatService.saveAndSend(messageDto);
    }
}
