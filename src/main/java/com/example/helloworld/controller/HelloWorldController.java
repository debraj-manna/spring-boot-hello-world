package com.example.helloworld.controller;

import brave.baggage.BaggageField;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@AllArgsConstructor
@Slf4j
public class HelloWorldController {
    @GetMapping("/hello")
    public ResponseEntity<StreamingResponseBody> sendGreetings() {
        logMsg("Hello Outside Body Thread");
        return ResponseEntity.ok()
            .body(
                outputStream ->
                {
                    logMsg("Hello Outside Body Thread");
                    outputStream.write("Hello World".getBytes());
                }
            );
    }

    @GetMapping("/hello1")
    public ResponseEntity<String> sendGreetings1() {
        logMsg("Hello1");
        return ResponseEntity.ok().body("Hello World");
    }

    private void logMsg(final String msg) {
        log.info("Thread: {} {}", Thread.currentThread().getName(), msg);
    }
}
