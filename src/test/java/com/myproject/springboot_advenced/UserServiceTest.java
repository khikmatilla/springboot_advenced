package com.myproject.springboot_advenced;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        //userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void successfullyCreated() {
        User user = User.builder()
                .userName("test")
                .email("test@email.com")
                .password("123")
                .build();

        when(userRepository.save(user)).thenReturn(User.builder()
                .id(3)
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build());

        User actual = userService.create(user);
        assertEquals(3, actual.getId());
        assertEquals("test", actual.getUserName());
        assertEquals("test@email.com", actual.getEmail());
        assertEquals("123", actual.getPassword());
        verify(userRepository, atLeast(1)).save(user); //save metodi ishlaganda userRepository dagi
        // methodlarni kamida 1 marta chaqirilganini
        // tekshiradi
    }

    @Test
    void cannotBeNull() {
//        User user = null;
//        when(userRepository.save(null)).thenThrow(new RuntimeException("User can't be null"));
        assertThrows(RuntimeException.class, () -> userService.create(null)).printStackTrace();
    }


    @Test
    void emailAlreadyExists() {
        User user = User.builder().email("john@doe.com").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        assertThrows(RuntimeException.class, () -> userService.create(user)).printStackTrace();
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void userNameAlreadyExists() {
        User user = User.builder().userName("John").email("jlkesh@Gmail.com").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        assertThrows(RuntimeException.class, () -> userService.create(user)).printStackTrace();
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).findByUsername(anyString());
    }


    @Test
    void successfulGetTest() {
        User user = new User(1, "John", "Doe", "john@doe.com");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User actual = userService.get(1);
        assertEquals(1, actual.getId());
        assertEquals(user.getEmail(), actual.getEmail());
        assertEquals(user.getUserName(), actual.getUserName());

        verify(userRepository, times(1)).findById(1);
    }

     @Test
    void failedGetTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.get(anyInt())).printStackTrace();

        verify(userRepository, times(1)).findById(anyInt());
    }



}