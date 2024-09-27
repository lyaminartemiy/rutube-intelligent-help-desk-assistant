package org.example.usecase.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final TechSupportRequestWebSocketHandler techSupportRequestWebSocketHandler;
//    private final DialogueWebSocketHandler dialogueWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(techSupportRequestWebSocketHandler, "/tech-support-requests").setAllowedOrigins("*");
//        registry.addHandler(dialogueWebSocketHandler, "/dialogue").setAllowedOrigins("*");
    }
}
