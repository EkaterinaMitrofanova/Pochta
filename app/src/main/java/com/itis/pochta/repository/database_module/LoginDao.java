package com.itis.pochta.repository.database_module;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.itis.pochta.model.response.LoginResponseBody;

import java.util.List;

@Dao
public interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoginResponseBody loginResponseBody);

    @Query("SELECT * FROM login")
    LiveData<List<LoginResponseBody>> getLogin();

    @Query("DELETE FROM login")
    void clearAll();
}