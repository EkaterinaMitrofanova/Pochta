package com.itis.pochta.repository;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @NonNull
    @Provides
    @Singleton
    public UserRepository getRepository(){
        return new UserRepository();
    }
}
