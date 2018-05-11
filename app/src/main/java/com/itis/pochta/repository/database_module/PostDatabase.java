package com.itis.pochta.repository.database_module;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.itis.pochta.model.response.LoginResponseBody;

@Database(entities = {LoginResponseBody.class}, version = 1, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase{
    public abstract LoginDao getLoginDao();
}
