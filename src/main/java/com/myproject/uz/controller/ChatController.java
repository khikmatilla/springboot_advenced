package com.myproject.uz.controller;

import com.myproject.uz.dto.ChatDto;
import com.myproject.uz.entity.ChatRoom;
import com.myproject.uz.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/my")
    public ResponseEntity<List<ChatDto>> getMyChats(Authentication authentication) {
        String username = authentication.getName(); // Spring Security orqali foydalanuvchi nomi
        return ResponseEntity.ok(chatService.getChatsForUser(username));
    }
}

