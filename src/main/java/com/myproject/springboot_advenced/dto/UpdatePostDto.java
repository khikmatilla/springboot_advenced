package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.myproject.springboot_advenced.entity.Post}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpdatePostDto implements Serializable {
    private Integer id;
    private String title;
    private String body;
}