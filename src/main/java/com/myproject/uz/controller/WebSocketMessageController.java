package com.myproject.uz.controller;

import com.myproject.uz.dto.MessageDto;
import com.myproject.uz.dto.MessageRequest;
import com.myproject.uz.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class WebSocketMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(MessageRequest messageRequest ) {

        //String senderUsername = authentication.getName();


        MessageDto savedMessage = messageService.saveMessage(messageRequest, messageRequest.getUsername());

        String queueName = "/queue/messages";
        messagingTemplate.convertAndSendToUser(
                messageRequest.getReceiverUsername(),
                queueName,
                messageRequest.getContent()
        );
    }
}
// messagingTemplate.convertAndSendToUser(
//        messageRequest.getReceiverUsername(),
//                "/queue/messages",
//                        messageRequest.getContent()
//        );