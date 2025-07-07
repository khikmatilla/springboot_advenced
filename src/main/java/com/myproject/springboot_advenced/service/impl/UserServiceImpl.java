package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.dto.UserCreateDto;
import com.myproject.springboot_advenced.entity.Users;
import com.myproject.springboot_advenced.events.OtpGenerateEvent;
import com.myproject.springboot_advenced.mapper.UsersMapper;
import com.myproject.springboot_advenced.repository.UsersRepository;
import com.myproject.springboot_advenced.service.OtpService;
import com.myproject.springboot_advenced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    // private final OtpService otpService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Users createUser(UserCreateDto userCreateDto) {
        Users users = usersMapper.toEntity(userCreateDto);
        usersRepository.save(users);
        //otpService.generateOtp(users);
        eventPublisher.publishEvent(new OtpGenerateEvent(this, users));
        return users;
    }
}
