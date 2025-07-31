package com.myproject.uz.controller;

import com.myproject.uz.dto.MessageDto;
import com.myproject.uz.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{receiverUsername}")
    public ResponseEntity<List<MessageDto>> getMessagesWithUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String receiverUsername) {
        String senderUsername = userDetails.getUsername();
        List<MessageDto> messages = messageService.getMessagesBetween(senderUsername, receiverUsername);
        return ResponseEntity.ok(messages);
    }
}
