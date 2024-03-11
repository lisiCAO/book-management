package com.fsd.librarymanagement.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Weather {
    private double temperature;
    private String description;
    private String iconUrl;

    public Weather(double temperature, String description, String iconUrl) {
        this.temperature = temperature;
        this.description = description;
        this.iconUrl = iconUrl;
    }
}
