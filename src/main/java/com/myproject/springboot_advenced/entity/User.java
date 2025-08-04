package com.myproject.springboot_advenced.entity;

import com.myproject.springboot_advenced.dto.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "websocket_user")
public class User {
    @Id
    private String nickName;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private Status status;

}
