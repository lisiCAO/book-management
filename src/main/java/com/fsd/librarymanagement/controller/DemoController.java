package com.fsd.librarymanagement.controller;

import com.fsd.librarymanagement.entity.Weather;
import com.fsd.librarymanagement.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class DemoController {

    private final WeatherService weatherService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class); // Logger for logging

    @Autowired
    public DemoController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping("/")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/leaders")
    public String showLeaders(Model model){
        try{
            Weather weather = weatherService.getWeatherData();
            model.addAttribute("weather", weather);
        } catch (RuntimeException e){
            logger.warn(String.valueOf(e));
        }
        return "leaders";
    }

    @GetMapping("/books")
    public String showSystems(){
        return "books/system";
    }
}
