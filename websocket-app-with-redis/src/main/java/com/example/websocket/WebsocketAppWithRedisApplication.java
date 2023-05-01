package com.example.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WebsocketAppWithRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketAppWithRedisApplication.class, args);
	}

}