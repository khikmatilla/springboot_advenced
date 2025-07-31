package com.myproject.uz.repository;

import com.myproject.uz.entity.ChatRoom;
import com.myproject.uz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT c FROM ChatRoom c WHERE c.user1.username = :username OR c.user2.username = :username")
    List<ChatRoom> findAllByUsername(@Param("username") String username);
    Optional<ChatRoom> findByUser1AndUser2(User user1, User user2);
}
