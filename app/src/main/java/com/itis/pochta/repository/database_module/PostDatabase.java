package com.itis.pochta.repository.database_module;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.itis.pochta.model.base.Acceptor;
import com.itis.pochta.model.base.Driver;
import com.itis.pochta.model.base.User;
import com.itis.pochta.model.response.LoginResponseBody;

@Database(entities = {LoginResponseBody.class, Acceptor.class, Driver.class, User.class}, version = 6, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase{
    public abstract LoginDao getLoginDao();
}
