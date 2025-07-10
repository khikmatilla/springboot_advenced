package com.myproject.springboot_advenced;

import com.myproject.springboot_advenced.posts.Post;
import com.myproject.springboot_advenced.posts.PostRepository;
import com.myproject.springboot_advenced.posts.Rating;
import com.myproject.springboot_advenced.user.Users;
import com.myproject.springboot_advenced.user.UsersRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringbootAdvencedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdvencedApplication.class, args);
    }

    //@Bean
    ApplicationRunner applicationRunner(UsersRepository usersRepository, PostRepository postRepository) {
        return args -> {
            Users jonny = new Users(1, "Jonny", "English");
            Users jimmy = new Users(2, "Jimmy", "Deep");
          usersRepository.saveAllAndFlush(Arrays.asList(jonny, jimmy));

          var jonnyPosts = List.of(
            new Post(1, "Learn GraphQl Today", "Learn GraphQl Today(Body)", Rating.FOUR_STARS, jimmy),
            new Post(2, "Spring Doc In Spring Boot", "Spring Doc In Spring Boot(Body)", Rating.TWO_STARS, jimmy),
            new Post(3, "Reactive Thinking", "Reactive Thinking(Body)", Rating.THREE_STARS, jonny),
            new Post(4, "Reactive Spring", "Reactive Spring(Body)", Rating.FIVE_STARS, jimmy)

          );
          postRepository.saveAll(jonnyPosts);
        };
    }

}
