package com.itis.pochta.model.base;

import com.google.gson.annotations.SerializedName;

public class MyPackage {

    @SerializedName("ticket")
    private String ticket;

    @SerializedName("volume")
    private String volume;

    @SerializedName("weight")
    private String weight;

    @SerializedName("status")
    private String status;

    @SerializedName("date")
    private long date;

    @SerializedName("location")
    private long locationId;

    @SerializedName("destination")
    private long destinationId;

    @SerializedName("source")
    private long sourceId;

    @SerializedName("location_address")
    private String location;

    @SerializedName("destination_address")
    private String destination;

    @SerializedName("source_address")
    private String source;

    @SerializedName("consumer_phone")
    private String phone;

    public MyPackage() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isDone(){
        return status.equals("Done");
    }
}
