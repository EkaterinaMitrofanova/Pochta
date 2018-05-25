package com.itis.pochta.repository;

import android.annotation.SuppressLint;

import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.repository.utils.ExceptionProvider;
import com.itis.pochta.repository.utils.ResponseLiveData;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableError;
import io.reactivex.schedulers.Schedulers;

/**
 * Use this for default loading and error handling
 * Result returns to 'set' method
 * Make {@link ResponseLiveData}.postBody(body) in {@code set} method if you don`t use caching
 */
public interface Loader<T> {

    void set(T body);

    @SuppressLint("CheckResult")
    default void load(Observable<BaseResponse<T>> observable, ResponseLiveData<T> responseLiveData) {
        responseLiveData.postStatus(ResponseLiveData.Status.LOADING);
        observable
                .observeOn(Schedulers.computation())
                .switchMap(listResult -> {
                    if (listResult.getCode() != 0) { //0 means STATUS_OK
                        return ObservableError.error(new ExceptionProvider(null).getServerException(listResult.getCode()));
                    }
                    return Observable.just(listResult);
                })
                .subscribe(
                        response -> {
                            responseLiveData.postStatus(ResponseLiveData.Status.HANDLE);
                            set(response.getBody());
                        },
                        throwable -> {
                            responseLiveData.postStatus(ResponseLiveData.Status.HANDLE);
                            responseLiveData.postError(throwable);
                        }
                );
    }

}
