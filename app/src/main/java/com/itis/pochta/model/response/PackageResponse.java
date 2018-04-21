package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;

public class PackageResponse {

    @SerializedName("id")
    private long packageId;

    public PackageResponse(long packageId) {
        this.packageId = packageId;
    }

    public PackageResponse() {
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }
}
