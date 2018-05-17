package com.itis.pochta.repository;

import com.itis.pochta.App;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.model.response.CitiesResponse;
import com.itis.pochta.model.response.PackageResponse;
import com.itis.pochta.model.response.StoragesResponse;
import com.itis.pochta.repository.net_module.PackageApi;
import com.itis.pochta.repository.utils.ResponseLiveData;

import javax.inject.Inject;

public class PackageRepository {
    @Inject
    PackageApi packageApi;

    @Inject
    UserRepository userRepository;

    public PackageRepository() {
        App.getComponent().inject(this);
    }

    public ResponseLiveData<CitiesResponse> getCities() {
        ResponseLiveData<CitiesResponse> data = new ResponseLiveData<>();
        Loader<CitiesResponse> loader = data::postBody;
        loader.load(packageApi.getCities(), data);
        return data;
    }

    public ResponseLiveData<MyPackage> getMyPackage(String ticket) {
        ResponseLiveData<MyPackage> data = new ResponseLiveData<>();
        Loader<MyPackage> loader = data::postBody;
        loader.load(packageApi.getPackage(ticket), data);
        return data;
    }

    public ResponseLiveData<StoragesResponse> getStorages(String cityId, String city) {
        ResponseLiveData<StoragesResponse> data = new ResponseLiveData<>();
        Loader<StoragesResponse> loader = data::postBody;
        loader.load(packageApi.getStorages(cityId, city), data);
        return data;
    }

    public ResponseLiveData<PackageResponse> issuePackage(PackageForm packageForm) {
        ResponseLiveData<PackageResponse> data = new ResponseLiveData<>();
        Loader<PackageResponse> loader = data::postBody;
        userRepository.getToken().observeForever(s -> {
            loader.load(packageApi.issuePackage(s, packageForm), data);
        });
        return data;
    }

}
