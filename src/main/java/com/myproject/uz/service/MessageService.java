package com.myproject.uz.service;

import com.myproject.uz.dto.MessageDto;
import com.myproject.uz.dto.MessageRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessagesWith(Long receiverId);

    MessageDto saveMessage(MessageRequest messageRequest, Authentication authentication);

    List<MessageDto> getMessagesBetween(String senderUsername, String receiverUsername);
}
