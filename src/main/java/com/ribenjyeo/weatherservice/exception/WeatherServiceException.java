package com.ribenjyeo.weatherservice.exception;

public class WeatherServiceException extends RuntimeException {

    public WeatherServiceException(String message) {
        super(message);
    }

    public WeatherServiceException(String message, Throwable e) {
        super(message, e);
    }

}