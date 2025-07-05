package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.CommentCreateDTO;
import com.myproject.springboot_advenced.dto.CommentDTO;
import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.PostDto;
import com.myproject.springboot_advenced.entity.Post;
import com.myproject.springboot_advenced.repository.PostRepository;
import com.myproject.springboot_advenced.resourc.CommentClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentClient commentClientResources;


    @Override
    public PostDto getPost(@NonNull Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id !!"));
        List<CommentDTO> comments = commentClientResources.getAllComments(id);
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .comments(comments)
                .build();
    }

    @Override
    public PostDto createPost(@NonNull PostCreateDto postCreateDtoDto) {
        Post post = Post.builder()
                .title(postCreateDtoDto.getTitle())
                .body(postCreateDtoDto.getBody())
                .build();
        postRepository.save(post);
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .build();
    }

    @Override
    public void saveAllComments(@NonNull List<CommentCreateDTO> dtos) {
       commentClientResources.saveAllComments(dtos);
    }
}
