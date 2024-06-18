package com.ribenjyeo.weatherservice.controller;

import com.ribenjyeo.weatherservice.model.weather.WeatherData;
import com.ribenjyeo.weatherservice.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public Mono<List<WeatherData>> getCurrentWeather(@RequestParam(defaultValue = "Москва") String city,
                                                     @RequestParam(defaultValue = "${weather.provider.source}") String source) {
        return weatherService.getCurrentWeather(city, source);
    }

    @GetMapping("/weekly")
    public Mono<List<WeatherData>> getWeeklyWeather(@RequestParam(defaultValue = "Москва") String city,
                                                    @RequestParam(defaultValue = "${weather.provider.source}") String source) {
        return weatherService.getWeeklyWeather(city, source);
    }

}
