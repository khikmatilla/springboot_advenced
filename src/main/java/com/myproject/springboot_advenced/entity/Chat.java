package com.myproject.springboot_advenced.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;
    private String message;
    private LocalDateTime createTime = LocalDateTime.now();

}
