package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}