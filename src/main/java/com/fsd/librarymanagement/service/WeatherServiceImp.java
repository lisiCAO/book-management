package com.fsd.librarymanagement.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.librarymanagement.entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImp implements WeatherService{
    @Value("${myapp.apikey}")
    private String apiKey;
    public Weather getWeatherData() {
        final String uri = "https://api.openweathermap.org/data/2.5/weather?q=Montreal&appid={apiKey}&units=metric&lang=en";
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            double temp = root.path("main").path("temp").asDouble();
            String description = root.path("weather").get(0).path("description").asText();
            String iconCode = root.path("weather").get(0).path("icon").asText();
            String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";

            return new Weather(temp, description, iconUrl);
        } catch (Exception e) {
            throw new RuntimeException("Fail to fetch weather data", e);
        }
    }
}
