package com.ribenjyeo.weatherservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeocodeResponse {
    private Response response;

    @Data
    public static class Response {
        @JsonProperty("GeoObjectCollection")
        private GeoObjectCollection geoObjectCollection;

        @Data
        public static class GeoObjectCollection {
            private MetaDataProperty metaDataProperty;
            private FeatureMember[] featureMember;

            @Data
            public static class MetaDataProperty {
                @JsonProperty("GeocoderResponseMetaData")
                private GeocoderResponseMetaData geocoderResponseMetaData;

                @Data
                public static class GeocoderResponseMetaData {
                    private BoundedBy boundedBy;
                    private String request;
                    private String results;
                    private String found;

                    @Data
                    public static class BoundedBy {
                        @JsonProperty("Envelope")
                        private Envelope envelope;

                        @Data
                        public static class Envelope {
                            private String lowerCorner;
                            private String upperCorner;
                        }
                    }
                }
            }

            @Data
            public static class FeatureMember {
                @JsonProperty("GeoObject")
                private GeoObject geoObject;

                @Data
                public static class GeoObject {
                    private MetaDataProperty metaDataProperty;
                    private String name;
                    private String description;
                    private BoundedBy boundedBy;
                    private String uri;
                    @JsonProperty("Point")
                    private Point point;

                    @Data
                    public static class MetaDataProperty {
                        @JsonProperty("GeocoderMetaData")
                        private GeocoderMetaData geocoderMetaData;

                        @Data
                        public static class GeocoderMetaData {
                            private String precision;
                            private String text;
                            private String kind;
                            @JsonProperty("Address")
                            private Address address;
                            @JsonProperty("AddressDetails")
                            private AddressDetails addressDetails;

                            @Data
                            public static class Address {
                                @JsonProperty("country_code")
                                private String countryCode;
                                private String formatted;
                                @JsonProperty("Components")
                                private Component[] components;

                                @Data
                                public static class Component {
                                    private String kind;
                                    private String name;
                                }
                            }

                            @Data
                            public static class AddressDetails {
                                @JsonProperty("Country")
                                private Country country;

                                @Data
                                public static class Country {
                                    @JsonProperty("AdressLine")
                                    private String addressLine;
                                    @JsonProperty("CountryNameCode")
                                    private String countryNameCode;
                                    @JsonProperty("CountryName")
                                    private String countryName;
                                    @JsonProperty("AdministrativeArea")
                                    private AdministrativeArea administrativeArea;

                                    @Data
                                    public static class AdministrativeArea {
                                        @JsonProperty("AdministrativeAreaName")
                                        private String administrativeAreaName;
                                    }
                                }
                            }
                        }
                    }

                    @Data
                    public static class BoundedBy {
                        @JsonProperty("Envelope")
                        private Envelope envelope;

                        @Data
                        public static class Envelope {
                            private String lowerCorner;
                            private String upperCorner;
                        }
                    }

                    @Data
                    public static class Point {
                        private String pos;
                    }
                }
            }
        }
    }
}
