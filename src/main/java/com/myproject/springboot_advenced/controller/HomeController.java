package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.security.AuthoritiesConstants;
import com.myproject.springboot_advenced.security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Welcome to public endpoint";
    }

    @GetMapping("/secure")
    public String secure() {
        boolean b = SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN);
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        String s = currentUserLogin.get();
        boolean b1 = SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER);
        return "You are authenticated!";
    }
}
