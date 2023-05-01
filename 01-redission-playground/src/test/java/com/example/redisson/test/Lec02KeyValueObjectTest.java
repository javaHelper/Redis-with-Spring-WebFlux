package com.example.redisson.test;

import com.example.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

public class Lec02KeyValueObjectTest extends BaseTest {

    // 127.0.0.1:6379> get student:1
    //"{\"@class\":\"com.example.redisson.test.dto.Student\",\"age\":10,\"city\":\"atlanta\",\"name\":\"marshal\"}"
    @Test
    public void keyValueObjectTest(){
        Student student = Student.builder().name("marshal").age(10).city("atlanta").build();
        RBucketReactive<Object> bucket = this.client.getBucket("student:1", JsonJacksonCodec.INSTANCE);
        Mono<Void> set = bucket.set(student);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::print)
                .then();
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }

    // 127.0.0.1:6379> get student:1
    //"{\"age\":10,\"city\":\"atlanta\",\"name\":\"marshal\"}"
    @Test
    public void keyValueObjectTest1(){
        Student student = Student.builder().name("marshal").age(10).city("atlanta").marks(Arrays.asList(1,2,3)).build();
        RBucketReactive<Object> bucket = this.client.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));
        Mono<Void> set = bucket.set(student);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::print)
                .then();
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }
}