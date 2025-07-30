package com.myproject.springboot_advenced.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true; // kerak bo‘lsa, WebSocket requestlar uchun same origin tekshiruvidan o‘tmaslik
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpMessageDestMatchers("/app/**").authenticated() // yuboriladigan
                .simpSubscribeDestMatchers("/user/**", "/queue/**").authenticated() // obuna bo‘lish
                .anyMessage().denyAll(); // qolgan barchasiga ruxsat yo‘q
    }
}
