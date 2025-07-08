package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.UpdatePostDto;
import com.myproject.springboot_advenced.entity.Post;
import com.myproject.springboot_advenced.repository.PostRepository;
import com.myproject.springboot_advenced.service.PostService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ConcurrentHashMap<Integer, Post> postCache = new ConcurrentHashMap<>();

    @Override
    public Post createPost(PostCreateDto postCreateDto) {
        return null;
    }

    @Override
    @SneakyThrows
    public Post get(Integer id) {
        Post cachedPost = postCache.get(id);
        if (cachedPost != null) {
            return cachedPost;
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        TimeUnit.SECONDS.sleep(2);
        postCache.put(id, post);
        return post;
    }

    @Override
    public void delete(Integer id) {
        postRepository.deleteById(id);
        postCache.remove(id);
    }

    @Override
    public void updatePost(UpdatePostDto dto) {
        Post post = postRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        postRepository.save(post);
        postCache.put(dto.getId(), post);
    }
}
