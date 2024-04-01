package com.example.helloworld.controller;

import com.example.helloworld.service.Profile;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelloWorldController {
    private final Profile profile;
    @GetMapping("/hello")
    public String sendGreetings() {
        return profile.method1();
    }
}
