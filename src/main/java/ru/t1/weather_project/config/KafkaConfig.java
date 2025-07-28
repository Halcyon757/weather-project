package ru.t1.weather_project.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic weatherTopic() {
        return TopicBuilder.name("weather-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
