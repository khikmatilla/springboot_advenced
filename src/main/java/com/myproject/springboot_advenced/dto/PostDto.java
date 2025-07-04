package com.myproject.springboot_advenced.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto implements Serializable {
    private Integer id;
    private String title;
    private String body;
    private List<CommentDTO> comments;
}
