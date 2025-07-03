package com.myproject.springboot_advenced;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.springboot_advenced.post.Post;
import com.myproject.springboot_advenced.post.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@EnableMongoAuditing
@SpringBootApplication
public class SpringbootAdvencedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdvencedApplication.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
       return ()-> Optional.of(1L);
    }

   // @Bean
    public ApplicationRunner runner(PostRepository postRepository, ObjectMapper objectMapper) {
        return args -> {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            List<Post>posts = objectMapper.readValue(url, new TypeReference<List<Post>>() {

            });
            postRepository.saveAll(posts);
        };
    }

}
