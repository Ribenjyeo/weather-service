package com.ribenjyeo.weatherservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {

    private String city;
    private double latitude;
    private double longitude;

}