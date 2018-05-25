package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackagesListResponse {

    @SerializedName("packages")
    private List<String> packages;

    public PackagesListResponse(List<String> packages) {
        this.packages = packages;
    }

    public PackagesListResponse() {
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }
}
