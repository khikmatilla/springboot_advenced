package com.myproject.uz.service;

import com.myproject.uz.dto.MessageDto;
import com.myproject.uz.dto.MessageRequest;
import com.myproject.uz.entity.ChatRoom;
import com.myproject.uz.entity.Message;
import com.myproject.uz.entity.User;
import com.myproject.uz.mapper.MessageMapper;
import com.myproject.uz.repository.ChatRoomRepository;
import com.myproject.uz.repository.MessageRepository;
import com.myproject.uz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService; // Token yoki session orqali current userni olish uchun
    private final MessageMapper messageMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public List<MessageDto> getMessagesWith(Long receiverId) {
        User currentUser = userService.getCurrentUser(); // session/token orqali
        List<Message> messages = messageRepository
                .findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
                        currentUser.getId(), receiverId,
                        currentUser.getId(), receiverId
                );
        return messageMapper.toDtoList(messages);
    }

    @Override
    public MessageDto saveMessage(MessageRequest messageRequest, Authentication authentication) {

        // String name = authentication.getName();

        User sender = userRepository.findByUsername("user")
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByUsername(messageRequest.getReceiverUsername())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Check if chat room exists or create new
        ChatRoom chatRoom = chatRoomRepository
                .findByUser1AndUser2(sender, receiver)
                .or(() -> chatRoomRepository.findByUser1AndUser2(receiver, sender))
                .orElseGet(() -> {
                    ChatRoom newChat = new ChatRoom();
                    newChat.setUser1(sender);
                    newChat.setUser2(receiver);
                    return chatRoomRepository.save(newChat);
                });

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(messageRequest.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setChatRoom(chatRoom);

        return messageMapper.toDto(messageRepository.save(message));
    }

    @Override
    public List<MessageDto> getMessagesBetween(String username1, String username2) {
        log.info(username1 + " : " + username2);
        User user1 = userRepository.findByUsername(username1)
                .orElseThrow(() -> new RuntimeException("User1 not found"));

        User user2 = userRepository.findByUsername(username2)
                .orElseThrow(() -> new RuntimeException("User2 not found"));

        // Ikkala foydalanuvchi o‘rtasidagi barcha xabarlarni topamiz
        List<Message> messages = messageRepository.findMessagesBetweenUsers(user1, user2);

        return messages.stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

}
