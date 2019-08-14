package com.optionringringtone.newringtonefree.object;

import com.google.gson.annotations.SerializedName;

public class KeyWord {

    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("default_suggest")
    private int defaultSuggest;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getDefaultSuggest() {
        return defaultSuggest;
    }

    public void setDefaultSuggest(int defaultSuggest) {
        this.defaultSuggest = defaultSuggest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
