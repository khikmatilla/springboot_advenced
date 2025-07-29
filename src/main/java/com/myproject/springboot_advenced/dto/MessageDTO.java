package com.myproject.springboot_advenced.dto;

import lombok.*;

@Getter
@Setter
public class MessageDTO {

    public String fromUserName;
    public String toUserName;
    public String message;
}
