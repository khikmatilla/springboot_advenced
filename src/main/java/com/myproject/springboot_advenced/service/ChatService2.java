//package com.myproject.springboot_advenced.service;
//
//import com.myproject.springboot_advenced.dto.ChatResponseDto;
//import com.myproject.springboot_advenced.dto.MessageDTO;
//import com.myproject.springboot_advenced.entity.Chat;
//import com.myproject.springboot_advenced.entity.User;
//import com.myproject.springboot_advenced.repository.ChatRepository;
//import com.myproject.springboot_advenced.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//@Service
//@Repository
//public class ChatService2 {
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private ChatRepository chatRepository;
//
//    public MessageDTO sendMessage(MessageDTO dto) {
//        User from = userRepo.findByUserName(dto.getFromUserName())
//                .orElseThrow(() -> new RuntimeException("From user not found"));
//        User to = userRepo.findByUserName(dto.getToUserName())
//                .orElseThrow(() -> new RuntimeException("To user not found"));
//
//        Chat chat = new Chat();
//        chat.setFromUser(from);
//        chat.setToUser(to);
//        chat.setMessage(dto.getMessage());
//
//        chatRepository.save(chat);
//
//        ChatResponseDto responseDto = new ChatResponseDto(
//                from.getUserName(),
//                to.getUserName(),
//                chat.getMessage(),
//                chat.getCreateTime()
//        );
//
//        messagingTemplate.convertAndSendToUser(
//                to.getUserName(),
//                "/queue/messages",
//                responseDto
//        );
//        return dto;
//    }
//}
