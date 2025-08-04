package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.dto.Status;
import com.myproject.springboot_advenced.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<Object> findByNickName(String nickName);
}