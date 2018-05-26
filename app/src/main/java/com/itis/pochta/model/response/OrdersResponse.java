package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;
import com.itis.pochta.model.base.Order;

import java.util.List;

public class OrdersResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("packages")
    private List<Order> orders;

    public OrdersResponse() {
    }

    public OrdersResponse(int status, List<Order> orders) {
        this.status = status;
        this.orders = orders;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
