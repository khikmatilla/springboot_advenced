package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.UpdatePostDto;
import com.myproject.springboot_advenced.entity.Post;
import com.myproject.springboot_advenced.repository.PostRepository;
import com.myproject.springboot_advenced.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;;


    @Override
    public Post createPost(PostCreateDto postCreateDto) {
        return null;
    }

    @Override
    @SneakyThrows
    @Cacheable(value = "post", key = "#id")
    public Post get(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        TimeUnit.SECONDS.sleep(2);
        return post;
    }

    @CacheEvict(value = "post", key = "#id")
    @Override
    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "post", key = "#dto.id")
    public Post updatePost(UpdatePostDto dto) {
        Post post = postRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        return postRepository.save(post);
    }

    @Override
    @Cacheable(value = "post", key = "#root.methodName")
    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
