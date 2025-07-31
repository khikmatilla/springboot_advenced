package com.myproject.uz.controller;

import com.myproject.uz.dto.ChatMessage;
import com.myproject.uz.entity.Message;
import com.myproject.uz.repository.MessageRepository;
import com.myproject.uz.security.SecurityUtils;
import com.myproject.uz.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.myproject.uz.security.SecurityUtils.getCurrentUsername;

@Controller
public class ChatController {

    private final ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void send(ChatMessage chatMessage) {
        chatService.sendMessage(chatMessage);

    }

    @GetMapping("/api/messages")
    public List<Message> getMyMessages() {
        return messageRepository.findAll();
    }
}