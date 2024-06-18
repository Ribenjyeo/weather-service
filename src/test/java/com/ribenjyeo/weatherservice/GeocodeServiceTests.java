package com.ribenjyeo.weatherservice;

import com.ribenjyeo.weatherservice.model.Coordinates;
import com.ribenjyeo.weatherservice.model.GeocodeResponse;
import com.ribenjyeo.weatherservice.service.GeocodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeocodeServiceTests {

    @Autowired
    private GeocodeService geocodeService;

    @MockBean
    private WebClient webClient;

    @Test
    void testGetCoordinates() {
        String city = "Москва";
        GeocodeResponse mockResponse = createMockGeocodeResponse(city);

        // Мокируем WebClient для метода GET
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        given(webClient.get()).willReturn(requestHeadersUriSpecMock);
        given(requestHeadersUriSpecMock.uri(anyString())).willReturn(requestHeadersSpecMock);
        given(requestHeadersSpecMock.retrieve()).willReturn(responseSpecMock);
        given(responseSpecMock.bodyToMono(GeocodeResponse.class)).willReturn(Mono.just(mockResponse));

        // Вызываем метод getCoordinates() и проверяем результат
        Mono<Coordinates> result = geocodeService.getCoordinates(city);

        StepVerifier.create(result)
                .expectNextMatches(response -> city.equals(response.getCity()))
                .verifyComplete();
    }

    private GeocodeResponse createMockGeocodeResponse(String city) {
        GeocodeResponse mockResponse = new GeocodeResponse();
        GeocodeResponse.Response.GeoObjectCollection.FeatureMember.GeoObject geoObject =
                new GeocodeResponse.Response.GeoObjectCollection.FeatureMember.GeoObject();
        GeocodeResponse.Response.GeoObjectCollection.FeatureMember.GeoObject.Point point =
                new GeocodeResponse.Response.GeoObjectCollection.FeatureMember.GeoObject.Point();
        point.setPos("37.617698 55.755864"); // Example coordinates for Moscow
        geoObject.setPoint(point);
        geoObject.setName(city);
        GeocodeResponse.Response.GeoObjectCollection.FeatureMember featureMember =
                new GeocodeResponse.Response.GeoObjectCollection.FeatureMember();
        featureMember.setGeoObject(geoObject);
        GeocodeResponse.Response.GeoObjectCollection geoObjectCollection = new GeocodeResponse.Response.GeoObjectCollection();
        geoObjectCollection.setFeatureMember(new GeocodeResponse.Response.GeoObjectCollection.FeatureMember[]{featureMember});
        GeocodeResponse.Response response = new GeocodeResponse.Response();
        response.setGeoObjectCollection(geoObjectCollection);
        mockResponse.setResponse(response);
        return mockResponse;
    }

}
