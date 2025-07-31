package com.myproject.uz.mapper;

import com.myproject.uz.dto.ChatDto;
import com.myproject.uz.dto.MessageDto;
import com.myproject.uz.entity.ChatRoom;
import com.myproject.uz.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(source = "user1.username", target = "user1")
    @Mapping(source = "user2.username", target = "user2")
    @Mapping(source = "messages", target = "messages")
    ChatDto toDto(ChatRoom chat);

    @Mapping(source = "sender.username", target = "sender")
    @Mapping(source = "receiver.username", target = "receiver")
    MessageDto toDto(Message message);

    List<ChatDto> toDtoList(List<ChatRoom> chats);
}
