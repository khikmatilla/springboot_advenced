//package com.myproject.springboot_advenced.service.impl;
//
//import com.myproject.springboot_advenced.entity.Users;
//import com.myproject.springboot_advenced.repository.UsersRepository;
//import com.myproject.springboot_advenced.service.OtpService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class OtpServiceImpl implements OtpService {
//
//    private final UsersRepository usersRepository;
//
//    @Override
//    public void generateOtp(Users users) {
//      users.setOtp(UUID.randomUUID().toString());
//      usersRepository.save(users);
//    }
//}
