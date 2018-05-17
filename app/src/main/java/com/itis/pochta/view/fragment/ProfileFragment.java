package com.itis.pochta.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentProfileBinding;
import com.itis.pochta.model.base.User;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.ViewListener;

public class ProfileFragment extends Fragment implements BaseView<User> {

    private FragmentProfileBinding binding;
    private ViewListener viewListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_profile);

        //todo: загрузить профиль [14] P.S.Нужно по роли узнать, кого загружать
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.actionLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: Logout
            }
        });
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
