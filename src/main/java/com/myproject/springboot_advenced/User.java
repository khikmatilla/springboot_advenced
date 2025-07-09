package com.myproject.springboot_advenced;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "email can not be blank")
    private String email;

    @NotBlank(message = "usrName can not be blank")
    private String userName;

    private String password;

    private String otp;
}
