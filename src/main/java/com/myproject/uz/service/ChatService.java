package com.myproject.uz.service;

import com.myproject.uz.dto.ChatMessageDto;
import com.myproject.uz.entity.ChatRoom;
import com.myproject.uz.entity.Message;
import com.myproject.uz.entity.User;
import com.myproject.uz.repository.ChatRoomRepository;
import com.myproject.uz.repository.MessageRepository;
import com.myproject.uz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    MessageRepository msgRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ChatRoomRepository chatRoomRepo;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public ChatRoom createChat(String from, String to) {
        if (from.equals(to)) throw new RuntimeException("Can't chat with yourself");
        User user1 = userRepo.findByUsername(from).orElseThrow();
        User user2 = userRepo.findByUsername(to).orElseThrow();
        return chatRoomRepo.save(new ChatRoom(null, user1, user2));
    }

    public void saveAndSend(ChatMessageDto dto) {
        User sender = userRepo.findByUsername(dto.getFrom()).orElseThrow();
        User receiver = userRepo.findByUsername(dto.getTo()).orElseThrow();
        ChatRoom room = chatRoomRepo.findById(dto.getChatId()).orElseThrow();

        Message msg = new Message(null, dto.getContent(), LocalDateTime.now(), sender, receiver, room);
        msgRepo.save(msg);
        messagingTemplate.convertAndSend("/topic/chat/" + dto.getChatId(), dto);
    }

    public List<ChatRoom> getUserChats(String username) {
        return chatRoomRepo.findByUser(username);
    }
}
