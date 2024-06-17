package com.example.helloworld.producer;

import com.example.helloworld.config.KafkaProducerConfig;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaStringProducer {
  private final KafkaTemplate<String, String> template;

  public void send(final String key, final String topic, final String value) {
    template.send(topic, key, value);
  }

}
