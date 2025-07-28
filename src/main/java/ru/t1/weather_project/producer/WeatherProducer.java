package ru.t1.weather_project.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.t1.weather_project.model.WeatherData;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherProducer.class);
    private final KafkaTemplate<String, WeatherData> kafkaTemplate;
    private final Random random = new Random();
    private final String[] conditions = {"sunny", "cloudy", "rain"};

    public WeatherProducer(KafkaTemplate<String, WeatherData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private WeatherData generateWeatherData() {
        int temperature = random.nextInt(36);
        String condition = conditions[random.nextInt(conditions.length)];
        return new WeatherData(temperature, condition);
    }

    @Scheduled(fixedRate = 5000)
    public void sendWeather() {
        WeatherData data = generateWeatherData();
        CompletableFuture<?> future = kafkaTemplate
                .send("weather-topic", data.getCondition(), data);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                LOGGER.error("Failed to send data: {}", ex.getMessage());
            } else {
                LOGGER.info("Sent: {}", data);
            }
        });
    }
}
