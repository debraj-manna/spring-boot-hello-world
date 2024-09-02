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
    public static final String CTX_KEY = "ctxName";
    @GetMapping("/hello")
    public ResponseEntity<StreamingResponseBody> sendGreetings() {
        val field = BaggageField.getByName(CTX_KEY);
        if (field != null) {
            field.updateValue("ctxValue");
        }
        log.info("Thread: {} Context: {}", Thread.currentThread().getName(), BaggageField.getByName(CTX_KEY).getValue());
        return ResponseEntity.ok()
            .body(
                outputStream ->
                {
                    log.info("Thread: {} Context: {}", Thread.currentThread().getName(), BaggageField.getByName(CTX_KEY).getValue());
                    outputStream.write("Hello World".getBytes());
                }
            );
    }

    @GetMapping("/hello1")
    public ResponseEntity<StreamingResponseBody> sendGreetings1() {
        val field = BaggageField.getByName(CTX_KEY);
        if (field != null) {
            field.updateValue("ctxValue");
        }
        log.info("Thread: {} Context: {}", Thread.currentThread().getName(), BaggageField.getByName(CTX_KEY).getValue());
        return ResponseEntity.ok()
            .body(
                outputStream ->
                {
                    log.info("Thread: {} Context: {}", Thread.currentThread().getName(), BaggageField.getByName(CTX_KEY).getValue());
                    outputStream.write("Hello World".getBytes());
                }
            );
    }
}
