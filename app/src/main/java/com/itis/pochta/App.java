package com.itis.pochta;

import android.app.Application;
import android.support.annotation.NonNull;

import com.itis.pochta.dagger.DaggerModelsComponent;
import com.itis.pochta.dagger.ModelsComponent;
import com.itis.pochta.repository.RepositoryModule;
import com.itis.pochta.repository.database_module.DatabaseModule;
import com.itis.pochta.repository.net_module.NetModule;


public class App extends Application {
    private static ModelsComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        component = getBuild();
    }

    @NonNull
    protected ModelsComponent getBuild() {
        return DaggerModelsComponent.builder()
                .netModule(new NetModule())
                .databaseModule(new DatabaseModule(getApplicationContext()))
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public static ModelsComponent getComponent() {
        return component;
    }
}
