package com.example.redisspring.weather.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ExternalServiceClient {

    @Cacheable(value = "weather", key = "#zip")
    public int getWeatherInfo(int zip) {
        return ThreadLocalRandom.current().nextInt(60,100);
    }
}