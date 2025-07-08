package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.UpdatePostDto;
import com.myproject.springboot_advenced.entity.Post;

public interface PostService {

    Post createPost(PostCreateDto postCreateDto);

    Post get(Integer id) throws InterruptedException;

    void delete(Integer id);

    void updatePost(UpdatePostDto updatePostDto);
}
