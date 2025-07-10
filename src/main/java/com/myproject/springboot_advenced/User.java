package com.myproject.springboot_advenced;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(otp, user.otp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, userName, password, otp);
    }
}
