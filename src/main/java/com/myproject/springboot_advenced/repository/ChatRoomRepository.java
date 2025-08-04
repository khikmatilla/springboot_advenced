package com.myproject.springboot_advenced.repository;

import com.myproject.springboot_advenced.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  @Query("SELECT c FROM ChatRoom c WHERE c.senderId = :sender AND c.recipientId = :recipient")
  Optional<ChatRoom> findBySenderIdAndRecipientId(@Param("sender") String senderNickName,
                                                  @Param("recipient") String recipientNickName);

}