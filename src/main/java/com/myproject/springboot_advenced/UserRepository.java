package com.myproject.springboot_advenced;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private static final List<User> users = new ArrayList<>();
    private static AtomicInteger generator = new AtomicInteger(3);

    static {
        users.add(new User(1, "John", "Doe", "john@doe.com"));
        users.add(new User(2, "Jim", "Carry", "jim@carry.com"));
    }

    public Optional<User> findById(Integer id) {
        log.info("Repository : Getting user by id: " + id);
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User save(User user) {
      if (user == null) {
        throw new RuntimeException("Persistent object can't be null");
      }
      user.setId(generator.incrementAndGet());
      users.add(user);
      log.info("Repository : Saving user: " + user);
      return user;
    }

    public Optional<User> findByEmail(String email){
      log.info("Repository : Getting user by email: " + email);
      return users.stream()
              .filter(user -> user.getEmail().equals(email))
              .findFirst();
    }

    public Optional<User> findByUsername(String username){
      log.info("Repository : Getting user by username: " + username);
      return users.stream()
              .filter(user -> user.getUserName().equals(username))
              .findFirst();
    }

}