package com.itis.pochta.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliverForm {

    @SerializedName("storage")
    private long storageId = -1;

    @SerializedName("packages")
    private List<String> tickets;

    public DeliverForm() {
    }

    public DeliverForm(long storageId, List<String> tickets) {
        this.storageId = storageId;
        this.tickets = tickets;
    }

    public long getStorageId() {
        return storageId;
    }

    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }
}
