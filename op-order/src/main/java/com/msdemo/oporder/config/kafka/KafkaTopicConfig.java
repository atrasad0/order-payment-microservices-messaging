package com.msdemo.oporder.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    private final String bootstrapAddress;
    private final String newOrdertopic;

    public KafkaTopicConfig(@Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress, @Value("${kafka.topic.new-order}") String newOrdertopic) {
        this.bootstrapAddress = bootstrapAddress;
        this.newOrdertopic = newOrdertopic;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
    @Bean
    public NewTopic createNewOrderTopic() {
        return TopicBuilder.name(newOrdertopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
