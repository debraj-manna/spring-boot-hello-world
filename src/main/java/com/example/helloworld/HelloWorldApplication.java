package com.example.helloworld;

import com.example.helloworld.aspects.DaoExceptionHandler;
import com.example.helloworld.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;

@Import({DaoExceptionHandler.class})
@SpringBootApplication
@EnableRetry
public class HelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}
