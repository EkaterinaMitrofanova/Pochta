package com.itis.pochta.repository.database_module;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.itis.pochta.model.base.Acceptor;
import com.itis.pochta.model.base.Driver;
import com.itis.pochta.model.base.User;
import com.itis.pochta.model.response.LoginResponseBody;

@Dao
public interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoginResponseBody loginResponseBody);

    @Query("SELECT * FROM login LIMIT 1")
    LiveData<LoginResponseBody> getLogin();

    @Query("SELECT * FROM login LIMIT 1")
    LoginResponseBody getLoginNotAsync();

    @Query("DELETE FROM login")
    void clearAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Acceptor acceptors);

    @Query("SELECT * FROM acceptor LIMIT 1")
    LiveData<Acceptor> getAcceptor();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Driver... acceptors);

    @Query("SELECT * FROM driver WHERE id=:id LIMIT 1")
    LiveData<Driver> getDriverById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    LiveData<User> getUserById(long id);
}
