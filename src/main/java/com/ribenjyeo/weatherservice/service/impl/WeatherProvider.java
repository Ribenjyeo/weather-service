package com.ribenjyeo.weatherservice.service.impl;

import com.ribenjyeo.weatherservice.model.Coordinates;
import com.ribenjyeo.weatherservice.model.weather.WeatherData;
import reactor.core.publisher.Mono;

public interface WeatherProvider {

    Mono<WeatherData> getCurrentWeather(Coordinates coordinates);

    Mono<WeatherData> getWeeklyWeather(Coordinates coordinates);

}
