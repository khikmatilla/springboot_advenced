package com.myproject.springboot_advenced.component;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestComponent {

    private final SimpMessagingTemplate messagingTemplate;

    public TestComponent(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 5000L)
    public void getDate(){
        messagingTemplate.convertAndSend("/topic/date", "Nmalar bilan Shugullanyapsan");
    }

}
