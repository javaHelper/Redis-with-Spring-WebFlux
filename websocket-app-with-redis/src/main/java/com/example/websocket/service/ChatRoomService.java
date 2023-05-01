package com.example.websocket.service;

import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ChatRoomService implements WebSocketHandler {
    @Autowired
    private RedissonReactiveClient client;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String room = getChatRoomName(webSocketSession);

        RTopicReactive topic = this.client.getTopic(room, StringCodec.INSTANCE);
        RListReactive<String> clientList = this.client.getList("history" + room, StringCodec.INSTANCE);

        // subscribe
        webSocketSession.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(msg -> clientList.add(msg).then(topic.publish(msg)))
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("Subscriber finally : " + s))
                .subscribe();

        //publisher
        Flux<WebSocketMessage> webSocketMessageFlux = topic.getMessages(String.class)
                .startWith(clientList.iterator())
                .map(webSocketSession::textMessage)
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("Publisher finally : " + s));

        return webSocketSession.send(webSocketMessageFlux);
    }

    private String getChatRoomName(WebSocketSession webSocketSession) {
        URI uri = webSocketSession.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }
}