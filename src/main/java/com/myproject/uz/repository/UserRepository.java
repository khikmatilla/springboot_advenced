package com.myproject.uz.repository;

import com.myproject.uz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.userName = :username")
  Optional<User> findByUserName(@Param("username") String userName);

}