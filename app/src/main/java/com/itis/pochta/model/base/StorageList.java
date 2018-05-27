package com.itis.pochta.model.base;

import java.io.Serializable;
import java.util.List;

public class StorageList implements Serializable{

    private List<MyStorage> storages;

    public StorageList(List<MyStorage> storages) {
        this.storages = storages;
    }

    public List<MyStorage> getStorages() {
        return storages;
    }

    public void setStorages(List<MyStorage> storages) {
        this.storages = storages;
    }
}
