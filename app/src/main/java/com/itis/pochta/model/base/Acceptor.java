package com.itis.pochta.model.base;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Acceptor {

    @PrimaryKey
    private long id = 1;

    @SerializedName("storage")
    private long storage;

    public Acceptor(long storage) {
        this.storage = storage;
    }

    public Acceptor() {
    }

    public long getId() {
        return 1;
    }

    public void setId(long id) {
    }

    public long getStorage() {
        return storage;
    }

    public void setStorage(long storage) {
        this.storage = storage;
    }
}
