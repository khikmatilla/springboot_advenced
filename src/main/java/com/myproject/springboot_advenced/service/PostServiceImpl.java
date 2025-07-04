package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.CommentDTO;
import com.myproject.springboot_advenced.dto.PostCreateDto;
import com.myproject.springboot_advenced.dto.PostDto;
import com.myproject.springboot_advenced.entity.Post;
import com.myproject.springboot_advenced.repository.PostRepository;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public PostDto getPost(@NonNull Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id !!"));

        RestTemplate restTemplate = new RestTemplate();
        String url = ("http://localhost:9595/api/comments/"+post.getId()+"/post");

        //ResponseEntity<List> response = restTemplate.getForEntity(url, List.class, post.getId());
        //List comments = response.getBody();

        //List comments = restTemplate.getForObject(url, List.class);

        ResponseEntity<List<CommentDTO>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                },
                post.getId()
        );
        List<CommentDTO> comments = responseEntity.getBody();


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
}
