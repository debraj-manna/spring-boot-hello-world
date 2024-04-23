package com.example.helloworld.controller;

import com.example.helloworld.dao.DaoImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class HelloWorldController {
    private final DaoImpl dao;
    @GetMapping("/hello")
    public String sendGreetings() {
        try {
            dao.getError();
        } catch (Exception e) {
            log.warn("Ignoring exception");
        }
        return "Hello, World!";
    }
}
