package com.itis.pochta.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.itis.pochta.App;
import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.model.response.LoginResponseBody;
import com.itis.pochta.repository.database_module.PostDatabase;
import com.itis.pochta.repository.net_module.UserApi;

import java.util.List;

import javax.inject.Inject;

public class UserRepository {
    private Loader<LoginResponseBody> loader;

    @Inject
    PostDatabase database;

    @Inject
    UserApi userApi;

    public UserRepository() {
        App.getComponent().inject(this);
        loader = new Loader<LoginResponseBody>() {
            @Override
            void set(BaseResponse<LoginResponseBody> result) {
                database.getLoginDao().clearAll();
                database.getLoginDao().insert(result.getBody());
            }
        };
    }

    /**
     * Return True if logged and false if not
     * This {@link LiveData} will update automatically if status will change
     */
    public LiveData<Boolean> isLoggedIn() {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        database.getLoginDao().getLogin().observeForever(loginResponseBodies -> {
            if ((loginResponseBodies != null ? loginResponseBodies.size() : 0) == 0) {
                liveData.postValue(false);
            } else {
                liveData.postValue(true);
            }
        });
        return liveData;
    }

    /**
     * ATTENTION!!! Use it only on Android main thread
     * Returns token if exists
     * Null if empty
     */
    @Nullable
    private String getToken() {
        List<LoginResponseBody> list = database.getLoginDao().getLogin().getValue();
        if ((list != null ? list.size() : 0) == 0) {
            return null;
        } else {
            return list.get(0).getToken();
        }
    }

    /**
     * Result will return to isLoggedIn method
     */
    public void logIn(LoginForm loginForm) {
        loader.load(userApi.login(loginForm));
    }

    /**
     * Result will return to isLoggedIn method
     */
    public void logOut() {
        database.getLoginDao().clearAll();
    }

    public LiveData<String> getErrorLiveData() {
        return loader.getErrorLiveData();
    }

    public LiveData<Statuses> getStatusLiveData() {
        return loader.getStatusLiveData();
    }

}
