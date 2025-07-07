package com.myproject.springboot_advenced.controller;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tasks implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private String label;
    private LocalDateTime createdAt;
}
