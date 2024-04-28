package com.example.helloworld.config;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

@Configuration
@Slf4j
@EnableLoadTimeWeaving
public class AspectConfig {

  @Bean
  public Map<Long, String> errorLogTime() {
    log.info("Initialising Bean");
    return Map.of();
  }
}
