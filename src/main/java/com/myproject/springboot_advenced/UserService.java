package com.myproject.springboot_advenced;


import lombok.NonNull;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(@NonNull User user) {
        String email = user.getEmail();

        Optional<User> userOptional;
        userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RuntimeException("Email already exists " + email);
        }
        String userName = user.getUserName();

        userOptional = userRepository.findByUsername(userName);
        if (userOptional.isPresent()) {
            throw new RuntimeException("UserName already token " + userName);
        }
        return userRepository.save(user);
    }

    public User get(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found " + id));
    }
}
