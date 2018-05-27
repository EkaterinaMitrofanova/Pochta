package com.itis.pochta.repository;

import com.itis.pochta.App;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.model.request.DeliverForm;
import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.model.request.PickUpForm;
import com.itis.pochta.model.response.CitiesResponse;
import com.itis.pochta.model.response.DeliverResponse;
import com.itis.pochta.model.response.OrdersResponse;
import com.itis.pochta.model.response.PackageResponse;
import com.itis.pochta.model.response.PackagesListResponse;
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

    public ResponseLiveData<StoragesResponse> getStorages(long cityId, String city) {
        ResponseLiveData<StoragesResponse> data = new ResponseLiveData<>();
        Loader<StoragesResponse> loader = data::postBody;
        loader.load(packageApi.getStorages(cityId, city), data);
        return data;
    }

    public ResponseLiveData<StoragesResponse> getStoragesForDriver(long cityId) {
        ResponseLiveData<StoragesResponse> data = new ResponseLiveData<>();
        Loader<StoragesResponse> loader = data::postBody;
        loader.load(packageApi.getStoragesForDriver(cityId), data);
        return data;
    }

    public ResponseLiveData<PackageResponse> issuePackage(PackageForm packageForm) {
        ResponseLiveData<PackageResponse> data = new ResponseLiveData<>();
        Loader<PackageResponse> loader = data::postBody;
        loader.load(packageApi.issuePackage(userRepository.getToken(), packageForm), data);
        return data;
    }

    public ResponseLiveData<Void> acceptPackage(String ticket) {
        ResponseLiveData<Void> data = new ResponseLiveData<>();
        Loader<Void> loader = data::postBody;
        loader.load(packageApi.acceptPackage(userRepository.getToken(), ticket), data);
        return data;
    }

    public ResponseLiveData<PackagesListResponse> getPackagesByPhoneAndId(String phone, long storageId) {
        ResponseLiveData<PackagesListResponse> data = new ResponseLiveData<>();
        Loader<PackagesListResponse> loader = data::postBody;
        loader.load(packageApi.getPackagesByPhone(phone, storageId), data);
        return data;
    }

    public ResponseLiveData<Void> pickUp(PickUpForm form, long storage) {
        ResponseLiveData<Void> data = new ResponseLiveData<>();
        Loader<Void> loader = data::postBody;
        loader.load(packageApi.pickUp(userRepository.getToken(), form, storage), data);
        return data;
    }

    public ResponseLiveData<OrdersResponse> getOrders(long storageId) {
        ResponseLiveData<OrdersResponse> data = new ResponseLiveData<>();
        Loader<OrdersResponse> loader = data::postBody;
        loader.load(packageApi.getOrders(userRepository.getToken(), storageId), data);
        return data;
    }

    public ResponseLiveData<OrdersResponse> getDriverOrders(){
        ResponseLiveData<OrdersResponse> data = new ResponseLiveData<>();
        Loader<OrdersResponse> loader = data::postBody;
        loader.load(packageApi.getDriverOrders(userRepository.getToken()), data);
        return data;
    }

    public ResponseLiveData<DeliverResponse> deliver(DeliverForm form){
        ResponseLiveData<DeliverResponse> data = new ResponseLiveData<>();
        Loader<DeliverResponse> loader = data::postBody;
        loader.load(packageApi.deliver(userRepository.getToken(), form), data);
        return data;
    }

}
