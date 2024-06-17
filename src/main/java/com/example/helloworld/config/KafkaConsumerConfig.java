package com.example.helloworld.config;

import com.example.helloworld.consumer.GossiperMessageListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.AbstractKafkaListenerContainerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

@Configuration
public class KafkaConsumerConfig {

  public static final String AT_LEAST_ONCE_KAFKA_CONTAINER_FACTORY =
      "atLeastOnceKafkaListenerContainerFactory";

  public Map<String, Object> consumerConfigBase() {
    val props = new HashMap<String, Object>();

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(
        ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
    props.put(
        ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
        300000);
    return props;
  }

  @Bean
  public AbstractKafkaListenerContainerFactory<
      ConcurrentMessageListenerContainer<String, String>, String, String>
  atLeastOnceKafkaListenerContainerFactory(GossiperMessageListener listener) {
    val factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.getContainerProperties().setAckMode(AckMode.RECORD);
    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfigBase()));
    factory.getContainerProperties().setMessageListener(listener);
    return factory;
  }
}
