package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.myproject.springboot_advenced.entity.Post}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDto implements Serializable {

    private String title;
    private String body;

}