package com.example.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04BucketAsMapTest extends BaseTest {

    // Before running this test case make sure you have data
    // 127.0.0.1:6379> SET user:1:name sam
    //OK
    //127.0.0.1:6379> SET user:2:name jake
    //OK
    //127.0.0.1:6379> SET user:3:name mike
    //OK
    //127.0.0.1:6379>
    @Test
    public void bucketAsMap() {

        Mono<Void> mono = this.client.getBuckets(StringCodec.INSTANCE)
                .get("user:1:name", "user:2:name", "user:3:name")
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(mono)
                .verifyComplete();
    }
}