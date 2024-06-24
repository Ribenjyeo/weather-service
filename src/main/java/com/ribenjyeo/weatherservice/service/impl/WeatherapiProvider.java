package com.ribenjyeo.weatherservice.service.impl;

import com.ribenjyeo.weatherservice.annotation.WeatherSource;
import com.ribenjyeo.weatherservice.exception.WeatherDataRetrievalException;
import com.ribenjyeo.weatherservice.model.Coordinates;
import com.ribenjyeo.weatherservice.model.WeatherapiResponse;
import com.ribenjyeo.weatherservice.model.weather.WeatherData;
import com.ribenjyeo.weatherservice.model.weather.WeatherInfo;
import com.ribenjyeo.weatherservice.service.WeatherProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@WeatherSource("weatherapi")
public class WeatherapiProvider implements WeatherProvider {

    private static final String OPENWEATHERMAP_URL = "http://api.weatherapi.com/v1/forecast.json";
    private static final String OPENWEATHERMAP_SERVICE_NAME = "weatherapi";
    private final WebClient webClient;
    private final String apiKey;

    @Autowired
    public WeatherapiProvider(WebClient.Builder webClientBuilder, @Value("${weather.service.weatherapi.api}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(OPENWEATHERMAP_URL).build();
        this.apiKey = apiKey;
    }

    @Override
    public Mono<WeatherData> getCurrentWeather(Coordinates coordinates) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", apiKey)
                        .queryParam("q", String.format(Locale.US, "%f,%f",
                                coordinates.getLatitude(), coordinates.getLongitude()))
                        .queryParam("days", 1)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleError)
                .bodyToMono(WeatherapiResponse.class)
                .map(s -> mapToWeatherData(s, coordinates.getCity()));
    }

    @Override
    public Mono<WeatherData> getWeeklyWeather(Coordinates coordinates) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", String.format(Locale.US, "%f,%f",
                                coordinates.getLatitude(), coordinates.getLongitude()))
                        .queryParam("days", 7)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleError)
                .bodyToMono(WeatherapiResponse.class)
                .map(s -> mapToWeatherData(s, coordinates.getCity()));
    }

    private Mono<? extends Throwable> handleError(ClientResponse clientResponse) {
        return Mono.error(new WeatherDataRetrievalException(OPENWEATHERMAP_SERVICE_NAME));
    }

    private WeatherData mapToWeatherData(WeatherapiResponse response, String city) {
        WeatherData weatherData = new WeatherData(OPENWEATHERMAP_SERVICE_NAME, city);
        List<WeatherInfo> weatherInfos = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        int currentHour = now.getHour();
        for (WeatherapiResponse.Forecast.ForecastDay forecastDay : response.getForecast().getForecastday()) {
            //Получим информацию о погоде на текущее время
            final WeatherapiResponse.Forecast.ForecastDay.Hour hour = forecastDay.getHour().get(currentHour);

            String date = forecastDay.getDate();
            int temperature = (int) hour.getTempC();
            double windSpeed = hour.getWindKph();
            String weatherCondition = hour.getCondition().getText();

            WeatherInfo weatherInfo = WeatherInfo.builder()
                    .date(date)
                    .temperature(temperature)
                    .windSpeed(windSpeed)
                    .weatherCondition(weatherCondition)
                    .build();
            weatherInfos.add(weatherInfo);
        }
        weatherData.setWeatherInfo(weatherInfos);

        return weatherData;
    }

}
