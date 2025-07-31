package com.myproject.uz.controller;

import com.myproject.uz.dto.ChatMessage;
import com.myproject.uz.entity.Message;
import com.myproject.uz.repository.MessageRepository;
import com.myproject.uz.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat")
    public void send(ChatMessage chatMessage, Principal principal) {
        Optional<String> currentUsername = SecurityUtils.getCurrentUsername();
        String trim = currentUsername.get().trim();

        chatMessage.setSender(trim);

        Message message = new Message();
        message.setSender(trim);
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