package com.itis.pochta.model.base;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("ticket")
    private String ticket;

    @SerializedName("volume")
    private String volume;

    @SerializedName("weight")
    private String weight;

    @SerializedName("interm_dest")
    private long destination;

    @SerializedName("interm_dest_address")
    private String destinationAddress;

    public Order() {
    }

    public Order(String ticket, String volume, String weight, long destination, String destinationAddress) {
        this.ticket = ticket;
        this.volume = volume;
        this.weight = weight;
        this.destination = destination;
        this.destinationAddress = destinationAddress;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public long getDestination() {
        return destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
