package com.optionringringtone.newringtonefree.object;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("org")
    private String org;
    @SerializedName("hostname")
    private String hostname;
    @SerializedName("loc")
    private String loc;
    @SerializedName("country")
    private String country;
    @SerializedName("region")
    private String region;
    @SerializedName("city")
    private String city;
    @SerializedName("ip")
    private String ip;

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
