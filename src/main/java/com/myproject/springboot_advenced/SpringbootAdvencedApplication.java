package com.myproject.springboot_advenced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringbootAdvencedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdvencedApplication.class, args);
    }
}
