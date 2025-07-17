package com.myproject.springboot_advenced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @GetMapping
    public String adminPage() {
        return "Admin Page";
    }

    @GetMapping("/get-all")
    public List<String> getAllAdmins() {
        return List.of("admin1", "admin2", "admin3", "admin4", "admin5", "admin6");
    }


}
