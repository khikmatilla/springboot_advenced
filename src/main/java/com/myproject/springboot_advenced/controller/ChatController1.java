package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.model.ChatMessage;
import com.myproject.springboot_advenced.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
//@Controller
public class ChatController1 {

    private final ChatService chatService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessage send(ChatMessage message){
        log.info(message.toString());
        return chatService.getAnswer(message);
    }
}
