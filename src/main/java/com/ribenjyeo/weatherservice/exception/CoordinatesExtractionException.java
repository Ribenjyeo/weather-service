package com.ribenjyeo.weatherservice.exception;

public class CoordinatesExtractionException extends WeatherServiceException {

    public CoordinatesExtractionException(String city) {
        super("Could not determine coordinates for city: " + city);
    }

    public CoordinatesExtractionException(String city, Throwable cause) {
        super("Could not determine coordinates for city:" + city, cause);
    }

}
