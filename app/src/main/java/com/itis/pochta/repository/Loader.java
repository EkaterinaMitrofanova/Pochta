package com.itis.pochta.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.itis.pochta.model.response.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableError;
import io.reactivex.schedulers.Schedulers;

public abstract class Loader<T> {
    private static int STATUS_OK = 0;

    private MutableLiveData<String> errorLiveData;
    private MutableLiveData<Statuses> statusLiveData;

    public Loader() {
        errorLiveData = new MutableLiveData<>();
        statusLiveData = new MutableLiveData<>();
        statusLiveData.setValue(Statuses.HANDLE);
    }

    abstract void set(BaseResponse<T> result);

    protected void onError(Throwable throwable){
        statusLiveData.postValue(Statuses.HANDLE);
        errorLiveData.postValue(throwable.getMessage());
    }

    protected void onSuccess(BaseResponse<T> response){
        statusLiveData.postValue(Statuses.HANDLE);
        errorLiveData.postValue(null);
        set(response);
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Statuses> getStatusLiveData() {
        return statusLiveData;
    }

    @SuppressLint("CheckResult")
    public void load(Observable<BaseResponse<T>> observable) {
        statusLiveData.postValue(Statuses.LOADING);
        observable
                .observeOn(Schedulers.computation())
                .switchMap(listResult -> {
                    if (listResult.getCode() != STATUS_OK) {
                        // TODO: 11.05.18 replace text with some kind of error store
                        return ObservableError.error(new Exception("Error from Server. Code:" + listResult.getCode()));
                    }
                    return Observable.just(listResult);
                })
                .subscribe(
                        this::onSuccess,
                        this::onError
                );
    }

}
