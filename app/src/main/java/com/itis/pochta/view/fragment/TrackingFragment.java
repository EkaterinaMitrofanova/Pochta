package com.itis.pochta.view.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentTrackingBinding;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.ViewListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TrackingFragment extends Fragment implements BaseView<MyPackage>{

    private FragmentTrackingBinding binding;
    private ViewListener viewListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracking, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_tracking);

        initViews();

        return binding.getRoot();
    }

    private void initViews(){

        binding.search.setOnClickListener(v -> {
            String ticket = binding.searchTicket.getText().toString();
            //todo: Загрузить инфу о посылке [5]
        });
    }

    @Override
    public void startLoading(boolean start) {
        viewListener.startLoading(start);
    }

    @Override
    public void fillViews(MyPackage myPackage){
        binding.packageInfo.setVisibility(View.VISIBLE);
        binding.setMypackage(myPackage);
        Date date = new Date(myPackage.getDate());
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        binding.date.setText(formatForDateNow.format(date));
    }
}
