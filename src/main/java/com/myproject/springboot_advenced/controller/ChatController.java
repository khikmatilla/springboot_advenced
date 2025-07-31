package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.dto.MessageDto;
import com.myproject.springboot_advenced.entity.ChatMessage;
import com.myproject.springboot_advenced.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageRepository repo) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageRepository = repo;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDto messageDto) {
        ChatMessage saved = new ChatMessage();
        saved.setFromUser(messageDto.getFrom());
        saved.setToUser(messageDto.getTo());
        saved.setContent(messageDto.getContent());
        chatMessageRepository.save(saved);

        messagingTemplate.convertAndSend("/topic/messages", messageDto);
    }
}



