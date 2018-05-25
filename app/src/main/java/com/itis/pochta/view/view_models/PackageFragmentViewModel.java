package com.itis.pochta.view.view_models;

import android.arch.lifecycle.ViewModel;

import com.itis.pochta.App;
import com.itis.pochta.model.response.CitiesResponse;
import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;

import javax.inject.Inject;

public class PackageFragmentViewModel extends ViewModel {
    @Inject
    PackageRepository repository;
    private ResponseLiveData<CitiesResponse> citiesResponse;

    public PackageFragmentViewModel() {
        App.getComponent().inject(this);
    }

    public ResponseLiveData<CitiesResponse> getCitiesResponse() {
        if (citiesResponse == null) {
            citiesResponse = repository.getCities();
        }
        return citiesResponse;
    }

    public void setNotLoaded() {
        citiesResponse = null;
    }
}
