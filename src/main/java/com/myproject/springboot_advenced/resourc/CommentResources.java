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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Service
public class CommentResources {

    private final WebClient webClient;

    @Value("${comments.url.postComments}")
    private String postCommentsUrl;

    @Value("${comments.url.saveComments}")
    private String saveCommentUrl;

    public CommentResources(WebClient webClient) {
        this.webClient = webClient;
    }


    public void saveAllComments(@NonNull List<CommentCreateDTO> dtos) {
        webClient
                .post()
                .uri(saveCommentUrl)
                .body(BodyInserters.fromValue(dtos))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<CommentDTO> getComments(@NonNull Integer id) {
        String url = ("http://localhost:9595/api/comments/"+id+"/post");

        List<CommentDTO> comments = webClient
                .get()                                // getRequest
                .uri(url, id)                         // uri berildi
                .retrieve()                           // response olindi
                .bodyToMono(new ParameterizedTypeReference<List<CommentDTO>>() {     // monoga ogirildi
                })
                .block();      // mono block ga ogirildi
        return comments;
    }


}
