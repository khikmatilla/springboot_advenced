package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.domain.Users;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByUserName(@NotNull String userName);

  boolean existsByUserName(@NotNull String userName);
}