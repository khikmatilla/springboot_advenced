package com.myproject.springboot_advenced;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
//@TestPropertySource(locations = "/app.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testSavedMethod() {
        User user = User.builder()
                .email("email@email.com")
                .userName("userName")
                .password("password")
                .otp("123")
                .build();
        User actual = userRepository.save(user);
        assertEquals("email@email.com", actual.getEmail());
        assertEquals("123", actual.getOtp());
    }
}
