package com.example.helloworld.consumer;

import com.example.helloworld.config.KafkaConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaStringConsumer {
  @KafkaListener(topics = "TOPIC_A", groupId = "TOPIC_A.group_id", containerFactory = KafkaConsumerConfig.AT_LEAST_ONCE_KAFKA_CONTAINER_FACTORY)
  public void listenTopicA(String message) {
    log.info("Received Message : {}", message);
  }
}
