package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;
import com.itis.pochta.model.base.Storage;

import java.util.List;

public class StoragesResponseBody {

    @SerializedName("storages")
    private List<Storage> storages;

    public StoragesResponseBody(List<Storage> storages) {
        this.storages = storages;
    }

    public StoragesResponseBody() {
    }

    public List<Storage> getStorages() {
        return storages;
    }

    public void setStorages(List<Storage> storages) {
        this.storages = storages;
    }
}
