package com.itis.pochta.model.request;

import com.google.gson.annotations.SerializedName;

public class PackageForm {

    @SerializedName("volume")
    private double volume;

    @SerializedName("user_login")
    private String user_login;

    @SerializedName("dest_id")
    private long dest_id;


    public PackageForm(double volume, String user_login, long dest_id) {
        this.volume = volume;
        this.user_login = user_login;
        this.dest_id = dest_id;
    }

    public PackageForm() {
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public long getDest_id() {
        return dest_id;
    }

    public void setDest_id(long dest_id) {
        this.dest_id = dest_id;
    }
}
