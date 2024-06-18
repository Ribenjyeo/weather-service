package com.ribenjyeo.weatherservice.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {

    private String source;
    private String city;
    private List<WeatherInfo> weatherInfo;

    public WeatherData(String source, String city) {
        this.source = source;
        this.city = city;
    }

}
