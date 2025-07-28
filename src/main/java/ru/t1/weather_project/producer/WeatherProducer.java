package ru.t1.weather_project.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.t1.weather_project.model.WeatherData;

import java.util.Random;

@Service
public class WeatherProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherProducer.class);
    private static final String TOPIC = "weather-topic";

    private final KafkaTemplate<String, WeatherData> kafkaTemplate;
    private final Random random = new Random();
    private final String[] conditions;
    private final int tempMin;
    private final int tempMax;

    public WeatherProducer(
            KafkaTemplate<String, WeatherData> kafkaTemplate,
            @Value("${weather.producer.conditions}") String conditionsCsv,
            @Value("${weather.temperature.min}")         int tempMin,
            @Value("${weather.temperature.max}")         int tempMax
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.conditions    = conditionsCsv.split("\\s*,\\s*");
        this.tempMin       = tempMin;
        this.tempMax       = tempMax;
    }

    @Scheduled(fixedRateString = "${weather.producer.interval-ms}")
    public void sendWeather() {
        WeatherData data = generateWeatherData();
        kafkaTemplate.send(TOPIC, data.getCondition(), data)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        LOGGER.error("Failed to send data: {}", ex.getMessage());
                    } else {
                        LOGGER.info("Sent to {}: {}", TOPIC, data);
                    }
                });
    }

    private WeatherData generateWeatherData() {
        int range = tempMax - tempMin + 1;
        int temperature = tempMin + random.nextInt(range);
        String condition = conditions[random.nextInt(conditions.length)];
        return new WeatherData(temperature, condition);
    }
}
