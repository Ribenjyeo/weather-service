package com.ribenjyeo.weatherservice.service.impl;

import com.ribenjyeo.weatherservice.annotation.WeatherSource;
import com.ribenjyeo.weatherservice.exception.WeatherDataRetrievalException;
import com.ribenjyeo.weatherservice.model.Coordinates;
import com.ribenjyeo.weatherservice.model.YandexWeatherResponse;
import com.ribenjyeo.weatherservice.model.weather.WeatherData;
import com.ribenjyeo.weatherservice.model.weather.WeatherInfo;
import com.ribenjyeo.weatherservice.service.WeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@WeatherSource("yandex")
public class YandexProvider implements WeatherProvider {

    private static final String YANDEX_URL = "https://api.weather.yandex.ru/v2/forecast/";
    private static final String HEADER_YANDEX_API_KEY = "X-Yandex-API-Key";
    private static final String YANDEX_SERVICE_NAME = "yandex";
    private final WebClient webClient;
    private final String apiKey;

    @Autowired
    public YandexProvider(WebClient.Builder webClientBuilder, @Value("${weather.service.yandex.api}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(YANDEX_URL).build();
        this.apiKey = apiKey;
    }

    @Override
    public Mono<WeatherData> getCurrentWeather(Coordinates coordinates) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", coordinates.getLatitude())
                        .queryParam("lon", coordinates.getLongitude())
                        .queryParam("limit", 1)
                        .build())
                .header(HEADER_YANDEX_API_KEY, apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleError)
                .bodyToMono(YandexWeatherResponse.class)
                .map(s -> mapToWeatherData(s, coordinates.getCity()));
    }

    @Override
    public Mono<WeatherData> getWeeklyWeather(Coordinates coordinates) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", coordinates.getLatitude())
                        .queryParam("lon", coordinates.getLongitude())
                        .queryParam("limit", 7)
                        .build())
                .header(HEADER_YANDEX_API_KEY, apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleError)
                .bodyToMono(YandexWeatherResponse.class)
                .map(s -> mapToWeatherData(s, coordinates.getCity()));
    }

    private Mono<? extends Throwable> handleError(ClientResponse clientResponse) {
        return Mono.error(new WeatherDataRetrievalException(YANDEX_SERVICE_NAME));
    }

    private WeatherData mapToWeatherData(YandexWeatherResponse response, String city) {
        WeatherData weatherData = new WeatherData(YANDEX_SERVICE_NAME, city);
        List<WeatherInfo> weatherInfos = new ArrayList<>();
        for (YandexWeatherResponse.Forecast forecast : response.getForecasts()) {
            String date = forecast.getDate();
            int temperature = forecast.getParts().getDay().getTempAvg();
            double windSpeed = forecast.getParts().getDay().getWindSpeed();
            String weatherCondition = forecast.getParts().getDay().getCondition();

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
