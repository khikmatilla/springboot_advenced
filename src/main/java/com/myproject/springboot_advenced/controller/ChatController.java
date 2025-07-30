package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.dto.ChatMessage;
import com.myproject.springboot_advenced.entity.Message;
import com.myproject.springboot_advenced.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat")
    public void send(ChatMessage chatMessage, Principal principal) {
        String senderUsername = principal.getName(); // JWT orqali bog‘langan foydalanuvchi

        chatMessage.setSender(senderUsername);

        Message message = new Message();
        message.setSender(senderUsername);
        message.setReceiver(chatMessage.getReceiver());
        message.setContent(chatMessage.getContent());
        messageRepository.save(message);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiver(), "/queue/messages", chatMessage);
    }

    @GetMapping("/api/messages")
    public List<Message> getMyMessages() {
        return messageRepository.findAll();
    }
}