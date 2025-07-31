package com.myproject.uz.service;

import com.myproject.uz.dto.ChatDto;
import com.myproject.uz.dto.ChatMessageDto;
import com.myproject.uz.entity.ChatRoom;
import com.myproject.uz.entity.Message;
import com.myproject.uz.entity.User;
import com.myproject.uz.mapper.ChatMapper;
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

    private final ChatRoomRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    public ChatService(ChatRoomRepository chatRepository, UserRepository userRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;

        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
    }

    public List<ChatDto> getChatsForUser(String username) {
        List<ChatRoom> allByUsername = chatRepository.findAllByUsername(username);
        return chatMapper.toDtoList(allByUsername);
    }
}

