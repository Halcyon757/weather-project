package ru.t1.weather_project.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.t1.weather_project.model.WeatherData;

@Service
public class WeatherConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherConsumer.class);

    @KafkaListener(topics = "weather-topic", groupId = "weather-group")
    public void consume(WeatherData data) {
        LOGGER.info("Received           : {}", data);
    }
}
