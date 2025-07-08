package com.myproject.springboot_advenced.events;

import com.myproject.springboot_advenced.entity.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;


@Getter
@RequiredArgsConstructor
public final class OtpGenerateEvent  {
    private final Users users;
}
