package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.UpdatePostDto;
import com.myproject.springboot_advenced.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    Post createPost(PostCreateDto postCreateDto);

    Post get(Integer id) throws InterruptedException;

    void delete(Integer id);

    Post updatePost(UpdatePostDto updatePostDto);

    List<Post> getAll();
}
