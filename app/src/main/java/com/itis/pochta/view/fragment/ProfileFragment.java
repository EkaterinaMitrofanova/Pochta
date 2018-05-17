package com.itis.pochta.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentProfileBinding;
import com.itis.pochta.repository.UserRepository;
import com.itis.pochta.view.ViewListener;
import com.itis.pochta.view.activity.LoginActivity;

import javax.inject.Inject;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    @Inject
    UserRepository repository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        ViewListener viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_profile);

        repository.isLoggedIn().observe(this, aBoolean -> {
            if (!aBoolean) {
                Intent starter = new Intent(getContext(), LoginActivity.class);
                starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(starter);
                getActivity().finish();
            }
        });

        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.actionLogout.setOnClickListener(v -> {
            repository.logOut();
        });
    }

    private void fill() {

    }
}
