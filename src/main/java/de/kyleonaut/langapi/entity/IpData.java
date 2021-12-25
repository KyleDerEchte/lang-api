package de.kyleonaut.langapi.entity;

import lombok.Data;

@Data
public class IpData {
    private String status;
    private String country;
    private String countryCode;
    private String region;
    private String regionName;
    private String city;
    private int zip;
    private float lat;
    private float lon;
    private String timezone;
    private String isp;
    private String org;
    private String as;
    private String query;
}