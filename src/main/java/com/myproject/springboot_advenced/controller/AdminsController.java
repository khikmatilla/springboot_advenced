package com.myproject.springboot_advenced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminsController {

    @GetMapping
    public String adminPage() {
        return "Admin Page";
    }

    @GetMapping("/allAdmins")
    public List<String> allAdmins() {
        return List.of("admin1", "admin2", "admin3");
    }
}
