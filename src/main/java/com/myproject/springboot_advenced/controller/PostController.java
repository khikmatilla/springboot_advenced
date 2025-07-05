package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.dto.CommentCreateDTO;
import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.PostDto;
import com.myproject.springboot_advenced.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postCreateDto) {
        return ResponseEntity.ok(postService.createPost(postCreateDto));
    }

    @PostMapping("/save-comment")
    public ResponseEntity<Void> createList(@RequestBody List<CommentCreateDTO> dtos) {
        postService.saveAllComments(dtos);
        return ResponseEntity.ok(null);
    }
}
