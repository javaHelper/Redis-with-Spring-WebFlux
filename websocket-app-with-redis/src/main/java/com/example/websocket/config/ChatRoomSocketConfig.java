package com.example.websocket.config;

import com.example.websocket.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@Configuration
public class ChatRoomSocketConfig {
    @Autowired
    private ChatRoomService chatRoomService;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, ChatRoomService> map = Map.of(
                "/chat", chatRoomService
        );
        return new SimpleUrlHandlerMapping(map, -1);
    }
}