package com.myproject.uz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringbootAdvencedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdvencedApplication.class, args);
    }

}
