package com.itis.pochta.model.base;

import com.google.gson.annotations.SerializedName;

public class Acceptor extends User{

    @SerializedName("storage")
    private long storage;

    public Acceptor(long storage) {
        this.storage = storage;
    }

    public Acceptor() {
    }

    public long getStorage() {
        return storage;
    }

    public void setStorage(long storage) {
        this.storage = storage;
    }
}
