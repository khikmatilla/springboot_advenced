package com.myproject.springboot_advenced.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link Users}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UsersUpdateDto implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
}