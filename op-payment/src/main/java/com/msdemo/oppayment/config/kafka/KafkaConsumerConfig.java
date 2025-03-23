package com.msdemo.oppayment.config.kafka;

import com.msdemo.oppayment.v1.transfer.record.OrderRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

        private final String bootstrapAddress;

        private final String newOrderGroupId;

        public KafkaConsumerConfig(@Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress,
                                   @Value("${kafka.group-id.new-order}") String newOrderGroupId ) {
                this.bootstrapAddress = bootstrapAddress;
                this.newOrderGroupId = newOrderGroupId;
        }

        @Bean
        public ConsumerFactory<String, OrderRecord> orderConsumerFactory() {
                Map<String, Object> props = new HashMap<>();
                props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
                props.put(ConsumerConfig.GROUP_ID_CONFIG, newOrderGroupId);
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
                props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
                props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
                props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.msdemo.oppayment.record");
                props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderRecord.class.getName());
                return new DefaultKafkaConsumerFactory<>(props);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, OrderRecord> orderKafkaListenerContainerFactory() {
                ConcurrentKafkaListenerContainerFactory<String, OrderRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
                factory.setConsumerFactory(orderConsumerFactory());
                return factory;
        }
}