package com.myproject.springboot_advenced;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(@NonNull User user) {
        log.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User get(@NonNull Integer id) {
        log.info("Getting user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        log.info("User successfully found: {}", userRepository.findById(id));
        return user;
    }

    @Override
    public void delete(@NonNull Integer id) {
       log.info("Deleting user by id: {}", id);
       userRepository.deleteById(id);
    }
}
