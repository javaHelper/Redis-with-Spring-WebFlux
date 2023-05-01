package com.example.redisspring.city.service;

import com.example.redisspring.city.client.CityClient;
import com.example.redisspring.city.dto.City;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

    private RMapReactive<String, City> cityMap;
    private RMapCacheReactive<String, City> cityMapCache;

//    public CityService(RedissonReactiveClient client) {
//        this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
//    }

    public CityService(RedissonReactiveClient client) {
        this.cityMapCache = client.getMapCache("city", new TypedJsonJacksonCodec(String.class, City.class));
    }

//    public Mono<City> getCity(final String zipCode){
//        return this.cityMap.get(zipCode)
//                            .onErrorResume(ex -> this.cityClient.getCity(zipCode));
//    }

    public Mono<City> getCity(final String zipCode){
        return this.cityMapCache.get(zipCode)
                .switchIfEmpty(this.cityClient.getCity(zipCode)
                        .flatMap(city -> this.cityMapCache.fastPut(zipCode, city, 10, TimeUnit.SECONDS).thenReturn(city)));
    }

    //@Scheduled(fixedRate = 10_000)
    public void updateCity(){
        this.cityClient.getAll()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    }

}