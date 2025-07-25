package com.myproject.springboot_advenced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SpringbootAdvencedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdvencedApplication.class, args);
    }

}
