package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.entity.ChatMessage;
import com.myproject.springboot_advenced.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true).orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                recipientId,
                true
        );
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }
}
