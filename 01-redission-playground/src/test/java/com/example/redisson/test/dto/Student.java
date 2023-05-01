package com.example.redisson.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private int age;
    private String city;
    private List<Integer> marks;
}