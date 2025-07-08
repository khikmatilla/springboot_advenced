package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.dto.UserCreateDto;
import com.myproject.springboot_advenced.entity.Users;
import com.myproject.springboot_advenced.service.CacheService;
import com.myproject.springboot_advenced.service.MailService;
import com.myproject.springboot_advenced.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final MailService mailService;
    private final CacheService cacheService;

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.status(201).body(userService.createUser(userCreateDto));
    }

    @PostMapping("/smtp/on-off")
    public ResponseEntity<Boolean> turnOnOffSmtpService() {
        return ResponseEntity.ok(mailService.turnOnOffSmtp());
    }

    @GetMapping
    public ResponseEntity<ConcurrentHashMap<Object, Map<Object,Object>>> getCache() {
        return ResponseEntity.ok(cacheService.getCache());
    }


}
