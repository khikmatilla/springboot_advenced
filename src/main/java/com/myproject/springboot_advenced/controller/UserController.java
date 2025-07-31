package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.entity.ChatUser;
import com.myproject.springboot_advenced.repository.ChatUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final ChatUserRepository userRepository;

    public UserController(ChatUserRepository repo) {
        this.userRepository = repo;
    }

    @GetMapping
    public List<ChatUser> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public ChatUser register(@RequestBody ChatUser user) {
        return userRepository.save(user);
    }
}
