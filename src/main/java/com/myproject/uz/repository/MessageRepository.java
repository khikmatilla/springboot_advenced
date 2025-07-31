package com.myproject.uz.repository;

import com.myproject.uz.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomIdOrderByTimestamp(Long chatId);
}