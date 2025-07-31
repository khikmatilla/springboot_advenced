package com.myproject.uz.repository;

import com.myproject.uz.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select c from ChatRoom c where c.user1.username = :username or c.user2.username = :username")
    List<ChatRoom> findByUser(@Param("username") String username);
}