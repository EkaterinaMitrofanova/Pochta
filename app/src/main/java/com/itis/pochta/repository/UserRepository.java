package com.itis.pochta.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

import com.itis.pochta.App;
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
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        database.getLoginDao().getLogin().observeForever(loginResponseBodies -> {
            if (loginResponseBodies == null) {
                liveData.postValue(false);
            } else {
                liveData.postValue(true);
            }
        });
        return liveData;
    }

    /**
     * Returns token if exists
     * Null if empty
     */
    LiveData<String> getToken() {
        return Transformations.map(database.getLoginDao().getLogin(), LoginResponseBody::getToken);
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
