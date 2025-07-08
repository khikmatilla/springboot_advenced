package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.UpdatePostDto;
import com.myproject.springboot_advenced.entity.Post;
import com.myproject.springboot_advenced.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createUser(@RequestBody PostCreateDto postCreateDto) {
        return ResponseEntity.status(201).body(postService.createPost(postCreateDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Integer id) throws InterruptedException {
        return ResponseEntity.ok(postService.get(id));
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Integer id) {
        postService.delete(id);
    }

    @PutMapping("/update/{id}")
    public void updatePost(@RequestBody UpdatePostDto updatePostDto) {
        postService.updatePost(updatePostDto);
    }


}
