package com.myproject.springboot_advenced.listener;

import com.myproject.springboot_advenced.entity.Users;
import com.myproject.springboot_advenced.events.OtpGenerateEvent;
import com.myproject.springboot_advenced.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class UserEventsListener {

    private final UsersRepository usersRepository;

    public UserEventsListener(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @EventListener
    public void generateOtpEventListener(OtpGenerateEvent event) {
        Users users = event.getUsers();
        users.setOtp(UUID.randomUUID().toString());
        log.info("Generate OTP with : {}", users);
        usersRepository.save(users);
    }
}
