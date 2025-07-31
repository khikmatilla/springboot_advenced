package com.myproject.uz.controller;

import com.myproject.uz.dto.RegisterDto;
import com.myproject.uz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDto dto) {
        userService.register(dto);
        return "redirect:/login";
    }

}
