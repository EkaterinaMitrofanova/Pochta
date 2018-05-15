package com.itis.pochta.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.itis.pochta.App;
import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.model.response.LoginResponseBody;
import com.itis.pochta.repository.database_module.PostDatabase;
import com.itis.pochta.repository.net_module.UserApi;
import com.itis.pochta.repository.utils.ResponseLiveData;

import javax.inject.Inject;

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
    @Nullable
    private String getToken() {
        LoginResponseBody list = database.getLoginDao().getLogin().getValue();
        if (list == null) {
            return null;
        } else {
            return list.getToken();
        }
    }

    public ResponseLiveData<LoginResponseBody> logIn(LoginForm loginForm) {
        ResponseLiveData<LoginResponseBody> data = new ResponseLiveData<>(() -> database.getLoginDao().getLogin());
        Loader<LoginResponseBody> loader = body -> {
            database.getLoginDao().clearAll();
            database.getLoginDao().insert(body);
        };
        loader.load(userApi.login(loginForm), data);
        return data;
    }

    /**
     * Result will return to isLoggedIn method
     */
    public void logOut() {
        database.getLoginDao().clearAll();
    }

}
