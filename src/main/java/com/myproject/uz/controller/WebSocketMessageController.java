package com.myproject.uz.controller;

import com.myproject.uz.dto.MessageDto;
import com.myproject.uz.dto.MessageRequest;
import com.myproject.uz.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class WebSocketMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(MessageRequest messageRequest ) {

        //String senderUsername = authentication.getName();


        MessageDto savedMessage = messageService.saveMessage(messageRequest, messageRequest.getUsername());

        messagingTemplate.convertAndSend(
                "/topic/messages/" + messageRequest.getReceiverUsername(),
                savedMessage
        );
    }
}
