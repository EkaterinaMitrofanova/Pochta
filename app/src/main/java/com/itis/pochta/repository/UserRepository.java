package com.itis.pochta.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

import com.itis.pochta.App;
import com.itis.pochta.model.base.Acceptor;
import com.itis.pochta.model.base.Driver;
import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.model.response.LoginResponseBody;
import com.itis.pochta.repository.database_module.PostDatabase;
import com.itis.pochta.repository.net_module.UserApi;
import com.itis.pochta.repository.utils.ResponseLiveData;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class UserRepository {
    @Inject
    PostDatabase database;

    @Inject
    UserApi userApi;

    public UserRepository() {
        App.getComponent().inject(this);
    }

    /**
     * Return True if logged and false if not
     * This {@link LiveData} will update automatically if status will change
     */
    public LiveData<Boolean> isLoggedIn() {
        return Transformations.map(
                database.getLoginDao().getLogin(),
                input -> input != null && input.getToken() != null && !input.getToken().isEmpty());
    }

    /**
     * Returns token if exists
     * Null if empty
     */
    LiveData<String> getToken() {
        return Transformations.map(database.getLoginDao().getLogin(), LoginResponseBody::getToken);
    }

    public LiveData<String> getRole() {
        return Transformations.map(database.getLoginDao().getLogin(), LoginResponseBody::getRole);
    }

    public ResponseLiveData<LoginResponseBody> getLoginResponse(@Nullable LoginForm loginForm) {
        ResponseLiveData<LoginResponseBody> data = new ResponseLiveData<>(() -> database.getLoginDao().getLogin());
        if (loginForm != null) {
            Loader<LoginResponseBody> loader = body -> {
                database.getLoginDao().clearAll();
                database.getLoginDao().insert(body);
            };
            loader.load(userApi.login(loginForm), data);
        }
        return data;
    }

    public ResponseLiveData<Acceptor> getAcceptorUser(long id, boolean isFetchNeeded) {
        ResponseLiveData<Acceptor> data = new ResponseLiveData<>(() -> database.getLoginDao().getAcceptorById(id));
        if (isFetchNeeded) {
            Loader<Acceptor> loader = body -> database.getLoginDao().insert(body);
            getToken().observeForever(s -> loader.load(userApi.getAcceptor(s, id), data));
        }
        return data;
    }

    public ResponseLiveData<Driver> getDriverUser(long id, boolean isFetchNeeded) {
        ResponseLiveData<Driver> data = new ResponseLiveData<>(() -> database.getLoginDao().getDriverById(id));
        if (isFetchNeeded) {
            Loader<Driver> loader = body -> database.getLoginDao().insert(body);
            getToken().observeForever(s -> loader.load(userApi.getDriver(s, id), data));
        }
        return data;
    }

    /**
     * Result will return to isLoggedIn method
     */
    @SuppressLint("CheckResult")
    public void logOut() {
        io.reactivex.Observable.just(true)
                .subscribeOn(Schedulers.computation())
                .subscribe(aBoolean -> database.getLoginDao().clearAll());
    }

}
