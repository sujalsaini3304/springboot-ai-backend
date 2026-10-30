package com.sujalsaini.springboot_ai.utils;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class weatherService {

    public record WeatherResponse(
            String city,
            double temperature,
            double feelsLike,
            int humidity,
            String description) {
    }

    public record OpenWeatherResponse(
            String name,
            Main main,
            List<Weather> weather) {

        public record Main(
                double temp,

                @JsonProperty("feels_like") double feelsLike,

                int humidity) {
        }

        public record Weather(
                String main,
                String description) {
        }
    }

    private final RestClient restClient;

    @Value("${openweather.api-key}")
    private String apiKey;

    public weatherService(
            RestClient.Builder builder,
            @Value("${openweather.base-url}") String baseUrl) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    @Tool(description = "Get the current weather for a city")
    public WeatherResponse getCurrentWeather(String city) {

        OpenWeatherResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/data/2.5/weather")
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .body(OpenWeatherResponse.class);

        return new WeatherResponse(
                response.name(),
                response.main().temp(),
                response.main().feelsLike(),
                response.main().humidity(),
                response.weather().get(0).description());
    }

}
