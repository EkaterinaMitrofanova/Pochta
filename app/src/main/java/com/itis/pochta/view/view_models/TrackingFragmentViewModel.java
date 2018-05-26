package com.itis.pochta.view.view_models;

import android.arch.lifecycle.ViewModel;

import com.itis.pochta.App;
import com.itis.pochta.model.base.Acceptor;
import com.itis.pochta.repository.UserRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;

import javax.inject.Inject;

public class TrackingFragmentViewModel extends ViewModel {
    @Inject
    UserRepository repository;
    private ResponseLiveData<Acceptor> acceptor;

    public TrackingFragmentViewModel() {
        App.getComponent().inject(this);
    }

    public ResponseLiveData<Acceptor> getAcceptor() {
        if (acceptor == null) {
            acceptor = repository.getAcceptorUser(repository.getUserId(), true);
        }
        return acceptor;
    }

    public void setNotLoaded() {
        acceptor = null;
    }
}
