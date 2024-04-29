package com.example.helloworld.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@Slf4j
@EnableSpringConfigured
public class AspectConfig {

  @Bean
  public Map<Long, String> errorLogMap() {
    log.info("Initialising Bean");
    return new HashMap<>();
  }
}
