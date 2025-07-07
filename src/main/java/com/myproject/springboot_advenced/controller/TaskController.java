package com.myproject.springboot_advenced.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;


    @GetMapping
    public ResponseEntity<List<Tasks>> getAll() {
        return ResponseEntity.ok(taskRepository.getAll());
    }

}
