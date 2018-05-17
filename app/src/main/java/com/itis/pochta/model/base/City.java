package com.itis.pochta.model.base;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    public City() {
    }

    public City(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "City{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return name;
    }
}
