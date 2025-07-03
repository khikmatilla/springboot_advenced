package com.myproject.springboot_advenced.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Post>> getAllWithPages(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "ASC", required = false) Sort.Direction sortDirection,
            @RequestParam String field
    ) {
        Sort sort = Sort.by(sortDirection, field);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(postRepository.findAll(pageable));
    }

    @GetMapping("/query")
    public ResponseEntity<List<Post>> getAllByParams(
            @RequestParam(defaultValue = "", required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int userId
    ) {
        //return ResponseEntity.ok(postRepository.findAllByCustomTitle(title, userId));
        String regex = "^"+ title + ".*";
        return ResponseEntity.ok(postRepository.findAllByTitleRegexAndUserIdGreaterThan(regex, userId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postRepository.save(post));
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Post updatingData) {

        Post post = postRepository.findById(updatingData.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updatingData.getTitle() != null) {
            post.setTitle(updatingData.getTitle());
        }
        if (updatingData.getBody() != null) {
            post.setBody(updatingData.getBody());
        }
        if (updatingData.getUserId() != null) {
            post.setUserId(updatingData.getUserId());
        }
        if (updatingData.getId() != null) {
            post.setId(updatingData.getId());
        }
        postRepository.save(post);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        postRepository.delete(post);
        return ResponseEntity.noContent().build();
    }

}
