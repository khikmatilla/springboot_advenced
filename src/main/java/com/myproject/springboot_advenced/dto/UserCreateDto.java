package com.myproject.springboot_advenced.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.myproject.springboot_advenced.entity.Users}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto implements Serializable {

    private String email;
    private String userName;
    @Size(min = 4, max = 20)
    private String password;
}