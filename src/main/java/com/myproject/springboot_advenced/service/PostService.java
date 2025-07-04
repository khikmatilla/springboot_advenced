package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.PostDto;
import lombok.NonNull;

public interface PostService {

    PostDto getPost(@NonNull Integer id);

    PostDto createPost(@NonNull PostCreateDto postCreateDtoDto);
}
