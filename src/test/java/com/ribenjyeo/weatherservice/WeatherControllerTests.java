package com.ribenjyeo.weatherservice;

import com.ribenjyeo.weatherservice.controller.WeatherController;
import com.ribenjyeo.weatherservice.model.weather.WeatherData;
import com.ribenjyeo.weatherservice.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.BDDMockito.given;

@WebFluxTest(WeatherController.class)
@ContextConfiguration(classes = {WeatherController.class, WeatherService.class})
class WeatherControllerTests {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private WeatherService weatherService;

    @Test
    void testGetCurrentWeather() {
        String city = "Moscow";
        List<WeatherData> mockWeatherData = List.of(new WeatherData("Yandex", city));

        given(weatherService.getCurrentWeather(city, "yandex")).willReturn(Mono.just(mockWeatherData));

        webClient.get().uri("/weather/current?city={city}&source=yandex", city)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(WeatherData.class)
                .isEqualTo(mockWeatherData);
    }

    @Test
    void testGetWeeklyWeather() {
        String city = "Moscow";
        List<WeatherData> mockWeeklyWeatherData = List.of(new WeatherData("Yandex", city));

        given(weatherService.getWeeklyWeather(city, "yandex")).willReturn(Mono.just(mockWeeklyWeatherData));

        webClient.get().uri("/weather/weekly?city={city}&source=yandex", city)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(WeatherData.class)
                .isEqualTo(mockWeeklyWeatherData);
    }

    @Test
    void testGetWeatherAllSources() {
        String city = "Moscow";
        List<WeatherData> mockCurrentWeatherData = List.of(new WeatherData("Yandex", city));
        List<WeatherData> mockWeeklyWeatherData = List.of(new WeatherData("OpenWeatherMap", city));

        given(weatherService.getCurrentWeather(city, "all")).willReturn(Mono.just(mockCurrentWeatherData));
        given(weatherService.getWeeklyWeather(city, "all")).willReturn(Mono.just(mockWeeklyWeatherData));

        webClient.get().uri("/weather/current?city={city}&source=all", city)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(WeatherData.class)
                .isEqualTo(mockCurrentWeatherData);

        webClient.get().uri("/weather/weekly?city={city}&source=all", city)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(WeatherData.class)
                .isEqualTo(mockWeeklyWeatherData);
    }

}
