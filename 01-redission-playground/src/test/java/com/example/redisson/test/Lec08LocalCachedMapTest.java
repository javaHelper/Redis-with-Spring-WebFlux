package com.example.redisson.test;

import com.example.redisson.test.config.RedissonConfig;
import com.example.redisson.test.dto.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class Lec08LocalCachedMapTest extends BaseTest {
    private RLocalCachedMap<Integer, Student> studentsMap;

    @BeforeAll
    public void setupClient() {
        RedissonConfig config = new RedissonConfig();
        RedissonClient redissonClient = config.getClient();

        LocalCachedMapOptions<Integer, Student> mapOptions = LocalCachedMapOptions.<Integer, Student>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE);

        studentsMap = redissonClient.getLocalCachedMap(
                "students",
                new TypedJsonJacksonCodec(Integer.class, Student.class),
                mapOptions);
    }

    @Test
    public void appServer1() {
        Student student1 = Student.builder().name("sam").age(10).city("atlanta").marks(List.of(1, 2, 3)).build();
        Student student2 = Student.builder().name("jake").age(30).city("miami").marks(List.of(10, 20, 30)).build();

        this.studentsMap.put(1, student1);
        this.studentsMap.put(2, student2);

        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println(i + "==>" + studentsMap.get(1)))
                .subscribe();

        sleep(600000);
    }

    @Test
    public void appServer2() {
        Student student1 = Student.builder().name("sam-updated").age(10).city("atlanta").marks(List.of(1, 2, 3)).build();
        this.studentsMap.put(1, student1);
    }
}