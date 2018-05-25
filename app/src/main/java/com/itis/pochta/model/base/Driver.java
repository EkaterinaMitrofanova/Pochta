package com.itis.pochta.model.base;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
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
