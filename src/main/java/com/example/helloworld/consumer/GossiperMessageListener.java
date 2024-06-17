package com.example.helloworld.consumer;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GossiperMessageListener extends AbstractConsumerSeekAware implements
    MessageListener<String, String> {

  @Override
  public void onPartitionsAssigned(
      Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
    val groupId = KafkaUtils.getConsumerGroupId();
    log.info("XXXXX groupId: {} assigned partitions: {}", groupId, assignments);
  }

  @Override
  public void onMessage(ConsumerRecord<String, String> data) {
    log.info("Received message with Key: {}", data.key());
  }
}
