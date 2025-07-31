package com.myproject.uz.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUsersService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthUsersService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
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