package com.myproject.uz.service;


import com.myproject.uz.dto.RegisterDto;
import com.myproject.uz.entity.User;
import com.myproject.uz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(RegisterDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + dto.getUsername());
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());
        return userRepository.save(user);
    }

    public List<User> getAllUsersExcept(String username) {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(currentUsername);
    }
}
