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

    public OrdersResponse(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
