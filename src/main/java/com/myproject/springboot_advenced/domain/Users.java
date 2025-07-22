package com.myproject.springboot_advenced.domain;

import com.myproject.springboot_advenced.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "clean_code_user")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean activated;


    @ManyToMany()
    @JoinTable(
            name = "user_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authorities_name", referencedColumnName = "name")}
    )
    private Set<Authority> authorities = new HashSet<>();

    public Boolean isActivated() {
        return activated;
    }
}
