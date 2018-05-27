package com.itis.pochta.model.base;

import java.io.Serializable;

public class MyStorage implements Serializable{

    private long id;
    private String name;
    private String address;
    private double lon;
    private double lat;

    public MyStorage(long id, String name, String address, double lon, double lat) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lon = lon;
        this.lat = lat;
    }

    public MyStorage() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return name;
    }
}
