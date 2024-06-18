package com.ribenjyeo.weatherservice.exception;

public class WeatherDataRetrievalException extends WeatherServiceException {

    public WeatherDataRetrievalException(String source) {
        super("An error occurred while retrieving the current weather from the service: " + source);
    }

    public WeatherDataRetrievalException(String source, Throwable e) {
        super("An error occurred while retrieving the current weather from the service: " + source, e);
    }
}
