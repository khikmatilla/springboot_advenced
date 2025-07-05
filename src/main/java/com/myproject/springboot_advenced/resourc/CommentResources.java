package com.myproject.springboot_advenced.resourc;

import com.myproject.springboot_advenced.dto.CommentCreateDTO;
import com.myproject.springboot_advenced.dto.CommentDTO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class CommentResources {

    private final RestTemplate restTemplate;

    @Value("${comments.url.postComments}")
    private String postCommentsUrl;

    @Value("${comments.url.saveComments}")
    private String saveCommentUrl;

    public CommentResources(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void saveAllComments(@NonNull List<CommentCreateDTO> dtos) {
        //restTemplate.postForEntity(url, dtos, Void.class);
        restTemplate.exchange(saveCommentUrl, HttpMethod.POST, new HttpEntity<>(dtos), Void.class);
    }

    public List<CommentDTO> getComments(@NonNull Integer id) {
        String url = ("http://localhost:9595/api/comments/"+id+"/post");

        //ResponseEntity<List> response = restTemplate.getForEntity(url, List.class, post.getId());
        //List comments = response.getBody();

        //List comments = restTemplate.getForObject(url, List.class);

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer token.........");
//        headers.setBearerAuth("token");
//        headers.setBasicAuth("username", "123");
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
//        HttpEntity<Object> entity = new HttpEntity<>(new Object(), headers);


        try {
            ResponseEntity<List<CommentDTO>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<>() {
                    },
                    id
            );
            List<CommentDTO> comments = responseEntity.getBody();
            return comments;
        }
        catch (ResourceAccessException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
