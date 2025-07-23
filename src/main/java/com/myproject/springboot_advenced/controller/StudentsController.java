package com.myproject.springboot_advenced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentsController {

    @GetMapping
    public String createStudent() {
        return "Create student Page";
    }

    @GetMapping("/allStudents")
    public List<String> allStudents() {
        return List.of("student1", "student2", "student3");
    }
}
