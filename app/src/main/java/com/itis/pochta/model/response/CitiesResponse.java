package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;
import com.itis.pochta.model.base.City;

import java.util.List;

public class CitiesResponse {

    @SerializedName("cities")
    private List<City> cities;

    public CitiesResponse(List<City> cities) {
        this.cities = cities;
    }

    public CitiesResponse() {
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
