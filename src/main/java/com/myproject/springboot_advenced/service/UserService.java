package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.Status;
import com.myproject.springboot_advenced.entity.User;
import com.myproject.springboot_advenced.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
     private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(User user) {
        User storedUser = (User) userRepository
                .findByNickName(user.getNickName()).orElseThrow(() -> new RuntimeException("User not found"));
        storedUser.setStatus(Status.OFFLINE);
        userRepository.save(storedUser);
    }

    public List<User> findOnlineUsers() {
        return userRepository.findAll();
    }
}
