package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.domain.Users;
import com.myproject.springboot_advenced.repository.UsersRepository;
import com.myproject.springboot_advenced.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {

        log.info("Registering user {}", user);
        if (!checkPasswordLength(user.getPassword())) {
            return new ResponseEntity("Password length must be between 4 and 16 characters", HttpStatus.CONFLICT);
        }
        if (usersService.checkUsername(user.getUserName())) {
            return new ResponseEntity("Username is already in use", HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(usersService.save(user));
    }

    public Boolean checkPasswordLength(String password) {
        return password.length() >= 4;
    }
}
