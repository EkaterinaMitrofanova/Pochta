package com.itis.pochta.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentProfileBinding;
import com.itis.pochta.model.base.User;
import com.itis.pochta.repository.UserRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.ViewListener;
import com.itis.pochta.view.activity.LoginActivity;
import com.itis.pochta.view.view_models.ProfileFragmentViewModel;

import javax.inject.Inject;

public class ProfileFragment extends Fragment implements BaseView<User> {

    private FragmentProfileBinding binding;
    private ViewListener viewListener;
    @Inject
    UserRepository repository;
    private ProfileFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_profile);

        viewModel = ViewModelProviders.of(this).get(ProfileFragmentViewModel.class);

        repository.isLoggedIn().observe(
                this,
                aBoolean -> {
                    if (!aBoolean) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
        );

        viewModel.getUser(repository.getUserId()).observe(
                this,
                this::fillViews,
                status -> startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> {
                    viewModel.setNotLoaded();
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.actionLogout.setOnClickListener(v -> repository.logOut());
    }

    @Override
    public void startLoading(boolean start) {
        viewListener.startLoading(start);
    }

    @Override
    public void fillViews(User o) {
        binding.setUser(o);
//        Picasso.with(getActivity())
//                .load(o.get)
//                .error(R.drawable.ic_no_photo)
//                .centerCrop()
//                .fit()
//                .into(mImageView);
    }
}
