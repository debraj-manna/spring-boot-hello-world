package com.example.helloworld.controller;

import com.example.helloworld.producer.KafkaStringProducer;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelloWorldController {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final KafkaStringProducer producer;
    @GetMapping("/hello")
    public String sendGreetings() {
        val i = counter.incrementAndGet();
        producer.send("k" + i, "TOPIC_A", "v" + i);
        return "Published";
    }
}
