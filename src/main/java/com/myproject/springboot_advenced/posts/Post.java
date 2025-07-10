package com.myproject.springboot_advenced.posts;

import com.myproject.springboot_advenced.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    private Integer id;
    private String title;
    private String body;
    private Rating rating;

    @ManyToOne(cascade = CascadeType.ALL)
    private Users users;
}
