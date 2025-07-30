package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.entity.AuthUser;
import com.myproject.springboot_advenced.repository.AuthUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser save(AuthUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authUserRepository.save(user);
    }

    public Boolean checkUsername(String username) {
        return authUserRepository.existsByUserName(username);
    }
}