package com.example.redisson.test;

import com.example.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec07MapCacheTest extends BaseTest {

    @Test
    public void mapCacheTest() {
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapCacheReactive<Integer, Student> mapCache = this.client.getMapCache("users:cache", codec);

        Student student1 = Student.builder().name("sam").age(10).city("atlanta").marks(List.of(1, 2, 3)).build();
        Student student2 = Student.builder().name("jake").age(30).city("miami").marks(List.of(10, 20, 30)).build();

        Mono<Student> mono1 = mapCache.put(1, student1, 5, TimeUnit.SECONDS);
        Mono<Student> mono2 = mapCache.put(2, student2, 10, TimeUnit.SECONDS);

        StepVerifier.create(mono1.concatWith(mono2).then())
                .verifyComplete();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();
    }
}