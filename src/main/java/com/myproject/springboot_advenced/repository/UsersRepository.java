package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}