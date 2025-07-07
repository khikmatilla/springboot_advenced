package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.dto.UserCreateDto;
import com.myproject.springboot_advenced.entity.Users;
import com.myproject.springboot_advenced.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.status(201).body(userService.createUser(userCreateDto));
    }
}
