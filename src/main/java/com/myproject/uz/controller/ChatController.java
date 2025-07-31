package com.myproject.uz.controller;

import com.myproject.uz.entity.ChatRoom;
import com.myproject.uz.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    ChatService chatService;

    @GetMapping
    public List<ChatRoom> userChats(Principal principal) {
        return chatService.getUserChats(principal.getName());
    }

    @PostMapping("/create")
    public ChatRoom create(@RequestParam String targetUsername, Principal principal) {
        return chatService.createChat(principal.getName(), targetUsername);
    }
}
