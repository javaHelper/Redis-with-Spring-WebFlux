package com.example.redisspring.fib.controller;

import com.example.redisspring.fib.service.FibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fib")
public class FibController {

    @Autowired
    private FibService fibService;

    @GetMapping("{index}")
    public Mono<Integer> getFib(@PathVariable int index) {
        return Mono.fromSupplier(() -> this.fibService.getFib(index));
    }

    @GetMapping("{index}/clear")
    public Mono<Void> clearCache(@PathVariable int index) {
        return Mono.fromRunnable(() -> this.fibService.clearCache(index));
    }

}