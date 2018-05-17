package com.itis.pochta.view.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentProfileBinding;
import com.itis.pochta.view.ViewListener;

public class ProfileFragment extends Fragment{

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        ViewListener viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_profile);

        initViews();
        return binding.getRoot();
    }

    private void initViews(){
        binding.actionLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: Logout
            }
        });
    }

    private void fill(){

    }
}
