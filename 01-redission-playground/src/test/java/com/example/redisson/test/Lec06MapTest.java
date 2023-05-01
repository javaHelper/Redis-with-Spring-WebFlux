package com.example.redisson.test;

import com.example.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class Lec06MapTest extends BaseTest {

    // 127.0.0.1:6379> HGETALL user:1
    //1) "name"
    //2) "sam"
    //3) "age"
    //4) "10"
    //5) "city"
    //6) "atlanta"
    //127.0.0.1:6379>
    @Test
    public void mapTest1() {
        RMapReactive<String, String> map = this.client.getMap("user:1", StringCodec.INSTANCE);
        Mono<String> name = map.put("name", "sam");
        Mono<String> age = map.put("age", "10");
        Mono<String> city = map.put("city", "atlanta");

        StepVerifier.create(name.concatWith(age).concatWith(city).then())
                .verifyComplete();
    }


    @Test
    public void mapTest2() {
        RMapReactive<String, String> map = this.client.getMap("user:2", StringCodec.INSTANCE);
        Map<String, String> javaMap = Map.of(
                "name", "jake",
                "age", "30",
                "city", "miami"
        );
        StepVerifier.create(map.putAll(javaMap).then())
                .verifyComplete();
    }

    // 127.0.0.1:6379> HGETALL users
    //1) "1"
    //2) "{\"age\":10,\"city\":\"atlanta\",\"marks\":[1,2,3],\"name\":\"sam\"}"
    //3) "2"
    //4) "{\"age\":30,\"city\":\"miami\",\"marks\":[10,20,30],\"name\":\"jake\"}"
    //127.0.0.1:6379>
    @Test
    public void mapTest3() {
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapReactive<Integer, Student> map = this.client.getMap("users", codec);

        Student student1 = Student.builder().name("sam").age(10).city("atlanta").marks(List.of(1, 2, 3)).build();
        Student student2 = Student.builder().name("jake").age(30).city("miami").marks(List.of(10, 20, 30)).build();

        Mono<Student> mono1 = map.put(1, student1);
        Mono<Student> mono2 = map.put(2, student2);

        StepVerifier.create(mono1.concatWith(mono2).then())
                .verifyComplete();
    }
}