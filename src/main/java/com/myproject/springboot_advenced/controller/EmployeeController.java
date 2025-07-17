package com.myproject.springboot_advenced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @GetMapping
    public String adminPage() {
        return "Employee Page";
    }

    @GetMapping("/get-all")
    public List<String> getAllEmployees() {
        return List.of("emp1", "emp2", "emp3", "emp4", "emp5", "emp6");
    }


}
