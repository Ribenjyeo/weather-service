package com.ribenjyeo.weatherservice.exception;

public class UnsupportedSourceException extends WeatherServiceException {

    public UnsupportedSourceException(String source) {
        super("Unsupported source: " + source);
    }

    public UnsupportedSourceException(String source, Throwable e) {
        super("Unsupported source: " + source, e);
    }

}
