package com.example.asyncprogramming.saxParser.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private int age;
    private String name;
    private String gender;
    private String role;

    public Person() {
    };

    @Override
    public String toString() {
        return "Person{" +
                "나이=" + age +
                ", 이름='" + name + '\'' +
                ", 성별='" + gender + '\'' +
                ", 직책='" + role + '\'' +
                '}';
    }
}
