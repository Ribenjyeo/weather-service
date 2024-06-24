package com.ribenjyeo.weatherservice.service;

import com.ribenjyeo.weatherservice.annotation.WeatherSource;
import com.ribenjyeo.weatherservice.exception.CoordinatesExtractionException;
import com.ribenjyeo.weatherservice.exception.UnsupportedSourceException;
import com.ribenjyeo.weatherservice.model.Coordinates;
import com.ribenjyeo.weatherservice.model.weather.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final List<WeatherProvider> weatherProviders;
    private final GeocodeService geocodeService;

    public Mono<List<WeatherData>> getCurrentWeather(String city, String source) {
        return geocodeService.getCoordinates(city)
                .onErrorResume(e -> Mono.error(new CoordinatesExtractionException(city, e)))
                .flatMap(coordinates -> {
                    if ("all".equalsIgnoreCase(source)) {
                        return getCurrentWeatherFromAllProviders(coordinates);
                    } else {
                        return getWeatherProvider(source)
                                .map(weatherProvider -> weatherProvider.getCurrentWeather(coordinates).flux().collectList())
                                .orElseThrow(() -> new UnsupportedSourceException(source));
                    }
                });
    }

    public Mono<List<WeatherData>> getWeeklyWeather(String city, String source) {
        return geocodeService.getCoordinates(city)
                .onErrorResume(e -> Mono.error(new CoordinatesExtractionException(city, e)))
                .flatMap(coordinates -> {
                    if ("all".equalsIgnoreCase(source)) {
                        return getWeeklyWeatherFromAllProviders(coordinates);
                    } else {
                        return getWeatherProvider(source)
                                .map(weatherProvider -> weatherProvider.getWeeklyWeather(coordinates).flux().collectList())
                                .orElseThrow(() -> new UnsupportedSourceException(source));
                    }
                });
    }

    private Mono<List<WeatherData>> getCurrentWeatherFromAllProviders(Coordinates coordinates) {
        List<Mono<WeatherData>> futures = weatherProviders.stream()
                .map(provider -> provider.getCurrentWeather(coordinates)
                        .onErrorResume(e -> {
                            log.error("Error occurred while getting current weather from provider: {}", provider.getClass().getName(), e);
                            return Mono.empty();
                        }))
                .toList();

        return Flux.merge(futures).collectList();
    }

    private Mono<List<WeatherData>> getWeeklyWeatherFromAllProviders(Coordinates coordinates) {
        List<Mono<WeatherData>> futures = weatherProviders.stream()
                .map(provider -> provider.getWeeklyWeather(coordinates)
                        .onErrorResume(e -> {
                            log.error("Error occurred while getting weekly weather from provider: {}", provider.getClass().getName(), e);
                            return Mono.empty();
                        }))
                .toList();

        return Flux.merge(futures).collectList();
    }

    private Optional<WeatherProvider> getWeatherProvider(String source) {
        return weatherProviders.stream()
                .filter(provider -> provider.getClass().isAnnotationPresent(WeatherSource.class) &&
                        provider.getClass().getAnnotation(WeatherSource.class).value().equalsIgnoreCase(source))
                .findFirst();
    }

}
