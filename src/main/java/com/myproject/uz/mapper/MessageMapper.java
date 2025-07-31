package com.myproject.uz.mapper;

import com.myproject.uz.dto.MessageDto;
import com.myproject.uz.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "sender", source = "sender.username")
    @Mapping(target = "receiver", source = "receiver.username")
    MessageDto toDto(Message message);
    List<MessageDto> toDtoList(List<Message> messages);
}
