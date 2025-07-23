package com.myproject.springboot_advenced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Welcome to public endpoint";
    }

    @GetMapping("/secure")
    public String secure() {
        return "You are authenticated!";
    }
}
