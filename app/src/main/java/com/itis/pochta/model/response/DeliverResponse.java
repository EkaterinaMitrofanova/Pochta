package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;
import com.itis.pochta.model.base.Order;

import java.util.List;

public class DeliverResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("packages")
    private List<Order> orders;

    public DeliverResponse() {
    }

    public DeliverResponse(String status, List<Order> orders) {
        this.status = status;
        this.orders = orders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
