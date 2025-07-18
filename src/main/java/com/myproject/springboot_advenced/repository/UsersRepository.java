package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.domain.Users;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  @Query(value = "select c from Users c where c.userName = ?1")
  Users findByUserName1(String userName);


  boolean findByUserName(@NotNull String userName);
}