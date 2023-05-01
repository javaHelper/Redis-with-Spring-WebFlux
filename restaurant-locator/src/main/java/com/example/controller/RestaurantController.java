package com.example.controller;

import com.example.dto.Restaurant;
import com.example.service.RestaurantLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("geo")
public class RestaurantController {

    @Autowired
    private RestaurantLocatorService locatorService;

    @GetMapping("{zip}")
    public Flux<Restaurant> getRestaurants(@PathVariable String zip){
        return this.locatorService.getRestaurants(zip);
    }

}