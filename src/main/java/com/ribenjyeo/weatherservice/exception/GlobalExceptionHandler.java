package com.ribenjyeo.weatherservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CoordinatesExtractionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleCoordinatesExtractionException(CoordinatesExtractionException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(WeatherServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleWeatherServiceException(WeatherServiceException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(WeatherDataRetrievalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleWeatherDataRetrievalException(WeatherDataRetrievalException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(UnsupportedSourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleUnsupportedSourceException(UnsupportedSourceException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusException(ResponseStatusException e) {
        log.error(e.getMessage(), e);
    }

}
