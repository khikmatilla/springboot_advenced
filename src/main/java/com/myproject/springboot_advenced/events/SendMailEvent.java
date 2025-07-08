package com.myproject.springboot_advenced.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public final class SendMailEvent {

    private final Integer id;
    private final String email;
    private final String otp;
}
