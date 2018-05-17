package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;

public class PackageResponse {

    @SerializedName("ticket")
    private String ticket;

    public PackageResponse() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
