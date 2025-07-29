package com.myproject.springboot_advenced.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String from;
    private String text;
}
