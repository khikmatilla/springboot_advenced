package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.entity.Users;

public interface OtpService {
    void generateOtp(Users users);
}
