package com.itis.pochta.view.view_models;

import android.arch.lifecycle.ViewModel;

import com.itis.pochta.App;
import com.itis.pochta.model.base.User;
import com.itis.pochta.repository.UserRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;

import javax.inject.Inject;

public class ProfileFragmentViewModel extends ViewModel {
    @Inject
    UserRepository repository;
    private ResponseLiveData<User> userResponseLiveData;

    public ProfileFragmentViewModel() {
        App.getComponent().inject(this);
    }

    public ResponseLiveData<User> getUser(long id) {
        if (userResponseLiveData == null) {
            userResponseLiveData = repository.getUser(id, true);
        }
        return userResponseLiveData;
    }
}
