package com.itis.pochta.dagger;

import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.repository.RepositoryModule;
import com.itis.pochta.repository.UserRepository;
import com.itis.pochta.repository.database_module.DatabaseModule;
import com.itis.pochta.repository.net_module.NetModule;
import com.itis.pochta.view.activity.LoginActivity;
import com.itis.pochta.view.activity.MainActivity;
import com.itis.pochta.view.fragment.OrdersFragment;
import com.itis.pochta.view.fragment.PackageFragment;
import com.itis.pochta.view.fragment.ProfileFragment;
import com.itis.pochta.view.fragment.StoragesFragment;
import com.itis.pochta.view.fragment.TrackingFragment;
import com.itis.pochta.view.view_models.PackageFragmentViewModel;
import com.itis.pochta.view.view_models.ProfileFragmentViewModel;
import com.itis.pochta.view.view_models.TrackingFragmentViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class, DatabaseModule.class, RepositoryModule.class})
public interface ModelsComponent {

    void inject(UserRepository userRepository);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(ProfileFragment profileFragment);

    void inject(PackageRepository packageRepository);

    void inject(PackageFragment packageFragment);

    void inject(TrackingFragment trackingFragment);

    void inject(ProfileFragmentViewModel profileFragmentViewModel);

    void inject(PackageFragmentViewModel packageFragmentViewModel);

    void inject(OrdersFragment ordersFragment);

    void inject(StoragesFragment storagesFragment);

    void inject(TrackingFragmentViewModel trackingFragmentViewModel);
}
