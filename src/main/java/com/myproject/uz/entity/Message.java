package com.myproject.uz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private ChatRoom chatRoom;


}
