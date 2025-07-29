package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}