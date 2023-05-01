package com.example.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

public class Lec05EventListenerPartTest extends BaseTest {

    @Test
    public void expiredEventTest() {
        RBucketReactive<Object> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        Mono<Void> event = bucket.addListener(new ExpiredObjectListener() {
            @Override
            public void onExpired(String s) {
                System.out.println("Expired : " + s);
            }
        }).then();

        StepVerifier.create(set.concatWith(get).concatWith(event))
                .verifyComplete();

        sleep(11000);
    }


    @Test
    public void deletedEventTest() {
        RBucketReactive<Object> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        Mono<Void> event = bucket.addListener(new DeletedObjectListener() {
            @Override
            public void onDeleted(String s) {
                System.out.println("Deleted : " + s);
            }
        }).then();

        StepVerifier.create(set.concatWith(get).concatWith(event))
                .verifyComplete();

        sleep(60000);
    }
}