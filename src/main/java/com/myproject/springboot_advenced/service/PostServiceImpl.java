package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.CommentCreateDTO;
import com.myproject.springboot_advenced.dto.CommentDTO;
import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.PostDto;
import com.myproject.springboot_advenced.entity.Post;
import com.myproject.springboot_advenced.repository.PostRepository;
import com.myproject.springboot_advenced.resourc.CommentResources;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentResources commentResources;

    public PostServiceImpl(PostRepository postRepository, CommentResources commentResources) {
        this.postRepository = postRepository;
        this.commentResources = commentResources;
    }

    @Override
    public PostDto getPost(@NonNull Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id !!"));
        List<CommentDTO> comments = commentResources.getComments(id);
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
       commentResources.saveAllComments(dtos);
    }
}
