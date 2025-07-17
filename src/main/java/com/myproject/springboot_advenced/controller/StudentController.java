package com.myproject.springboot_advenced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @GetMapping
    public String studentPage() {
        return "Student Page";
    }

    @GetMapping("/get-all")
    public List<String> getAllStudents() {
        return List.of("student1", "student2", "student3", "student4", "student5", "student6");
    }


}
