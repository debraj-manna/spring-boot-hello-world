package com.example.helloworld.consumer;

import com.example.helloworld.config.KafkaConsumerConfig;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaStringConsumer {
  @KafkaListener(topics = "TOPIC_B", groupId = "TOPIC_B.group_id", containerFactory = KafkaConsumerConfig.AT_LEAST_ONCE_KAFKA_CONTAINER_FACTORY)
  public void listenTopicA(String message) {
    log.info("Started processing message : {}", message);
    try {
      Thread.sleep(TimeUnit.SECONDS.toMillis(30));
    } catch (Exception e) {

    }
    log.info("Processed Message : {}", message);
  }
}
