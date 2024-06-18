package com.example.helloworld.controller;

import com.example.helloworld.producer.KafkaStringProducer;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class HelloWorldController {
    private final KafkaStringProducer producer;
    @GetMapping("/hello")
    public String sendGreetings() {
        for(var i = 0; i < 8; i++) {
            producer.send("k" + i, "TOPIC_B", "v" + i);
            log.info("Published message {}", i);
        }
        return "Published";
    }
}
