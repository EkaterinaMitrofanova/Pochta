package com.itis.pochta.model.base;

import com.google.gson.annotations.SerializedName;

public class Driver extends User{

    @SerializedName("type")
    private int type;

    @SerializedName("car")
    private long car;

    public Driver() {
    }

    public Driver(long car) {
        this.car = car;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCar() {
        return car;
    }

    public void setCar(long car) {
        this.car = car;
    }
}
