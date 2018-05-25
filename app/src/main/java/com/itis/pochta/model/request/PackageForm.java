package com.itis.pochta.model.request;

import com.google.gson.annotations.SerializedName;

public class PackageForm {

    @SerializedName("volume")
    private String volume;

    @SerializedName("weight")
    private String weight;

    @SerializedName("consumer_phone")
    private String consumer_phone;

    @SerializedName("dest_id")
    private long dest_id;


    public PackageForm(String volume, String consumer_phone, long dest_id) {
        this.volume = volume;
        this.consumer_phone = consumer_phone;
        this.dest_id = dest_id;
    }

    public PackageForm() {
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getConsumer_phone() {
        return consumer_phone;
    }

    public void setConsumer_phone(String consumer_phone) {
        this.consumer_phone = consumer_phone;
    }

    public long getDest_id() {
        return dest_id;
    }

    public void setDest_id(long dest_id) {
        this.dest_id = dest_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
