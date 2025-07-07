package com.myproject.springboot_advenced.events;

import com.myproject.springboot_advenced.entity.Users;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public final class OtpGenerateEvent extends ApplicationEvent {

    private final Users users;

    public OtpGenerateEvent(Object source, Users users) {
        super(source);
        this.users = users;
    }
}
