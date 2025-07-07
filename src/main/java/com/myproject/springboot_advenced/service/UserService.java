package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.UserCreateDto;
import com.myproject.springboot_advenced.entity.Users;

public interface UserService {

    Users createUser(UserCreateDto userCreateDto);
}
