package com.myproject.springboot_advenced.post;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document("posts")
public class Post {

    @Id
    @Field("post_id")
    private String postId;

    @Field("post_title")
    //@Indexed(name = "post_title_unique_index", unique = true, sparse = true)   // sparse -> postTitle yoq bolgan
    private String title;                                          // documentlar uchun index yaratmay otib ketadi

    @Field("post_body")
    private String body;

    @Field("user_id")
    private Integer userId;

    private Integer id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;
}
