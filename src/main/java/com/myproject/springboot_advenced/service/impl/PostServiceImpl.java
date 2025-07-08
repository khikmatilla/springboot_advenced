package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.UpdatePostDto;
import com.myproject.springboot_advenced.entity.Post;
import com.myproject.springboot_advenced.repository.PostRepository;
import com.myproject.springboot_advenced.service.PostService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CacheManager cacheManager;
    private final Cache cache ;

    public PostServiceImpl(PostRepository postRepository, CacheManager cacheManager) {
        this.postRepository = postRepository;
        this.cacheManager = cacheManager;
        this.cache = cacheManager.getCache("post");
    }

    @Override
    public Post createPost(PostCreateDto postCreateDto) {
        return null;
    }

    @Override
    @SneakyThrows
    public Post get(Integer id) {
        Post cachedPost = cache.get(id, Post.class);
        if (cachedPost != null) {
            return cachedPost;
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        TimeUnit.SECONDS.sleep(2);
        cache.put(id, post);
        return post;
    }

    @Override
    public void delete(Integer id) {
        postRepository.deleteById(id);
        cache.evict(id);
    }

    @Override
    public void updatePost(UpdatePostDto dto) {
        Post post = postRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        postRepository.save(post);
        cache.put(dto.getId(), post);
    }
}
