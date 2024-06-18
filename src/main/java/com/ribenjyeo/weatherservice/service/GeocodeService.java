package com.ribenjyeo.weatherservice.service;

import com.ribenjyeo.weatherservice.model.Coordinates;
import com.ribenjyeo.weatherservice.model.GeocodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeocodeService {

    private static final String GEOCODE_URL = "https://geocode-maps.yandex.ru";
    private final WebClient webClient;
    private final String apiKey;

    @Autowired
    public GeocodeService(WebClient.Builder webClientBuilder, @Value("${weather.service.geocode.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(GEOCODE_URL).build();
        this.apiKey = apiKey;
    }

    public Mono<Coordinates> getCoordinates(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/1.x/")
                        .queryParam("geocode", city)
                        .queryParam("apikey", apiKey)
                        .queryParam("format", "json")
                        .queryParam("results", 1)
                        .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class)
                .map(this::getCoordinates);
    }

    private Coordinates getCoordinates(GeocodeResponse geocodeResponse) {
        // Логика извлечения координат из ответа
        String pos = geocodeResponse.getResponse().getGeoObjectCollection().getFeatureMember()[0].getGeoObject().getPoint().getPos();
        String[] coordinates = pos.split(" ");
        double lon = Double.parseDouble(coordinates[0]);
        double lat = Double.parseDouble(coordinates[1]);
        String cityName = geocodeResponse.getResponse().getGeoObjectCollection().getFeatureMember()[0].getGeoObject().getName();
        return new Coordinates(cityName, lat, lon);
    }

}
