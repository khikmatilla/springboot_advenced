package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.entity.AuthUser;
import com.myproject.springboot_advenced.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser save(AuthUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public Boolean checkUsername(String username) {
        return usersRepository.existsByUserName(username);
    }
}