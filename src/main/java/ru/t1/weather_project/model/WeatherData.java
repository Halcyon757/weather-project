package ru.t1.weather_project.model;

public class WeatherData {

    private int temperature;
    private String condition;

    public WeatherData() {
    }

    public WeatherData(int temperature, String condition) {
        this.temperature = temperature;
        this.condition = condition;
    }

    public int getTemperature() {
        return temperature;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "temperature=" + temperature +
                ", condition='" + condition + '\'' +
                '}';
    }
}
