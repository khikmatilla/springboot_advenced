package com.myproject.uz.service;

import com.myproject.uz.dto.ChatMessage;
import com.myproject.uz.entity.Message;
import com.myproject.uz.repository.MessageRepository;
import com.myproject.uz.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {

    private final MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(ChatMessage chatMessage) {
        chatMessage.setSender(getCurrentUsername());

        Message message = new Message();
        message.setSender(getCurrentUsername());
        message.setReceiver(chatMessage.getReceiver());
        message.setContent(chatMessage.getContent());
        messageRepository.save(message);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiver(), "/queue/messages", chatMessage);
    }

    public String getCurrentUsername() {
        Optional<String> currentUsername = SecurityUtils.getCurrentUsername();
        String s = currentUsername.get();
        return s;
    }
}
