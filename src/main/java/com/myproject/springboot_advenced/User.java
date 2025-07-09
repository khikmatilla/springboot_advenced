package com.myproject.springboot_advenced;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    private Integer id;
    private String userName;
    private String email;
    private String password;
}
