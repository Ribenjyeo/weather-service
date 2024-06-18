package com.ribenjyeo.weatherservice.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {

    private String date;
    private int temperature;
    private double windSpeed;
    private String weatherCondition;

}
