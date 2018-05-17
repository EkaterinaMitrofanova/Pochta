package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;
import com.itis.pochta.model.base.MyStorage;

import java.util.List;

public class StoragesResponse {

    @SerializedName("storages")
    private List<MyStorage> myStorages;

    public StoragesResponse(List<MyStorage> myStorages) {
        this.myStorages = myStorages;
    }

    public StoragesResponse() {
    }

    public List<MyStorage> getMyStorages() {
        return myStorages;
    }

    public void setMyStorages(List<MyStorage> myStorages) {
        this.myStorages = myStorages;
    }
}
