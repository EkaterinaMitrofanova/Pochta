package com.itis.pochta.repository.net_module;

import android.support.annotation.NonNull;

import com.itis.pochta.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {
    private static final String BASE_URL = "https://kpfu-itis-logistic.herokuapp.com/";

    @NonNull
    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    @NonNull
    @Provides
    @Singleton
    public HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return interceptor;
    }

    @NonNull
    @Provides
    @Singleton
    public PackageApi getPackageApi(Retrofit retrofit) {
        return retrofit.create(PackageApi.class);
    }

    @NonNull
    @Provides
    @Singleton
    public UserApi getUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }
}
