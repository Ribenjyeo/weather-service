package com.ribenjyeo.weatherservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class YandexWeatherResponse {

    private long now;

    @JsonProperty("now_dt")
    private String nowDt;

    private Info info;
    private GeoObject geoObject;
    private Fact fact;
    private List<Forecast> forecasts;

    @Data
    public static class Info {
        private boolean n;
        private int geoid;
        private String url;
        private double lat;
        private double lon;
        private Tzinfo tzinfo;

        @JsonProperty("def_pressure_mm")
        private int defPressureMm;

        @JsonProperty("def_pressure_pa")
        private int defPressurePa;

        private String slug;
        private int zoom;
        private boolean nr;
        private boolean ns;
        private boolean nsr;
        private boolean p;
        private boolean f;

        @JsonProperty("_h")
        private boolean h;
    }

    @Data
    public static class Tzinfo {
        private String name;
        private String abbr;
        private boolean dst;
        private int offset;
    }

    @Data
    public static class GeoObject {
        private District district;
        private Locality locality;
        private Province province;
        private Country country;
    }

    @Data
    public static class District {
        private int id;
        private String name;
    }

    @Data
    public static class Locality {
        private int id;
        private String name;
    }

    @Data
    public static class Province {
        private int id;
        private String name;
    }

    @Data
    public static class Country {
        private int id;
        private String name;
    }

    @Data
    public static class Fact {
        @JsonProperty("obs_time")
        private long obsTime;

        private long uptime;
        private int temp;

        @JsonProperty("feels_like")
        private int feelsLike;

        private String icon;
        private String condition;
        private double cloudness;

        @JsonProperty("prec_type")
        private int precType;

        @JsonProperty("prec_prob")
        private int precProb;

        @JsonProperty("prec_strength")
        private int precStrength;

        @JsonProperty("is_thunder")
        private boolean isThunder;

        @JsonProperty("wind_speed")
        private double windSpeed;

        @JsonProperty("wind_dir")
        private String windDir;

        @JsonProperty("pressure_mm")
        private int pressureMm;

        @JsonProperty("pressure_pa")
        private int pressurePa;

        private int humidity;
        private String daytime;
        private boolean polar;
        private String season;
        private String source;

        @JsonProperty("uv_index")
        private int uvIndex;

        @JsonProperty("wind_gust")
        private double windGust;
    }

    @Data
    public static class Forecast {
        private String date;

        @JsonProperty("date_ts")
        private long dateTs;

        private int week;
        private String sunrise;
        private String sunset;

        @JsonProperty("rise_begin")
        private String riseBegin;

        @JsonProperty("set_end")
        private String setEnd;

        @JsonProperty("moon_code")
        private int moonCode;

        @JsonProperty("moon_text")
        private String moonText;

        private Parts parts;
    }

    @Data
    public static class Parts {
        private Part day;
        @JsonProperty("day_short")
        private Part dayShort;
        private Part evening;
        private Part morning;
        private Part night;
        @JsonProperty("night_short")
        private Part nightShort;
    }

    @Data
    public static class Part {
        @JsonProperty("_source")
        private String source;

        @JsonProperty("temp_min")
        private int tempMin;

        @JsonProperty("temp_avg")
        private int tempAvg;

        @JsonProperty("temp_max")
        private int tempMax;

        @JsonProperty("wind_speed")
        private double windSpeed;

        @JsonProperty("wind_gust")
        private double windGust;

        @JsonProperty("wind_dir")
        private String windDir;

        @JsonProperty("pressure_mm")
        private int pressureMm;

        @JsonProperty("pressure_pa")
        private int pressurePa;

        private int humidity;

        @JsonProperty("prec_mm")
        private int precMm;

        @JsonProperty("prec_prob")
        private int precProb;

        @JsonProperty("prec_period")
        private int precPeriod;

        private double cloudness;

        @JsonProperty("prec_type")
        private int precType;

        @JsonProperty("prec_strength")
        private int precStrength;

        private String icon;
        private String condition;

        @JsonProperty("uv_index")
        private int uvIndex;

        @JsonProperty("feels_like")
        private int feelsLike;

        private Biomet biomet;
        private String daytime;
        private boolean polar;

        @JsonProperty("fresh_snow_mm")
        private int freshSnowMm;
    }

    @Data
    public static class Biomet {
        private int index;
        private String condition;
    }
}

