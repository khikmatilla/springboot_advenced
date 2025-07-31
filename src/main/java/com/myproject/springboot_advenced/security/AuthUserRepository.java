package com.myproject.springboot_advenced.security;

import com.myproject.springboot_advenced.entity.AuthUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

  Optional<AuthUser> findByUserName(@NotNull String userName);

  boolean existsByUserName(@NotNull String userName);
}