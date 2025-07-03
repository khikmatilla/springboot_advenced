package com.myproject.springboot_advenced.post;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{post_title:  {$regex: '^?0.*'}, user_id: {$gt: ?1}}")
    List<Post> findAllByCustomTitle(String title, int userId);

    List<Post> findAllByTitleRegexAndUserIdGreaterThan(String title, int userId);
}
